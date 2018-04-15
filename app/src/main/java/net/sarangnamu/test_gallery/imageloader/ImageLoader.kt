package net.sarangnamu.test_gallery.imageloader

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.support.annotation.DrawableRes
import android.support.v4.graphics.BitmapCompat
import android.support.v4.util.LruCache
import android.text.format.Formatter
import android.widget.ImageView
import net.sarangnamu.common.BkSystem
import net.sarangnamu.test_gallery.common.AppConfig
import okhttp3.*
import okhttp3.Cache.key
import okhttp3.internal.Util
import okhttp3.internal.cache.CacheRequest
import okhttp3.internal.cache.DiskLruCache
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.http.HttpMethod
import okhttp3.internal.io.FileSystem
import okhttp3.logging.HttpLoggingInterceptor
import okio.Okio
import okio.Source
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 12.. <p/>
 */

class ImageLoader(val activity: Activity, @DrawableRes val placeholderId: Int) {
    companion object {
        private val log = LoggerFactory.getLogger(ImageLoader::class.java)

        private val K_LOAD = 1
    }

    private val handlerThread = HandlerThread("imageLoaderThread", Process.THREAD_PRIORITY_BACKGROUND)
    private val handler: Handler
    private var memCache = MemoryCache(activity)
    private var okhttp: OkHttpClient? = null

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper, ProgressHandler(this))
    }

    class ProgressHandler(val loader: ImageLoader) : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                K_LOAD -> loader.processLoad(msg.obj as ImageLoaderParams)
            }

            return true
        }
    }

    private fun sendMessage(what: Int, obj: Any) {
        handler.sendMessage(handler.obtainMessage(what, obj))
    }

    fun load(params: ImageLoaderParams) {
        // default image
        if (placeholderId != 0) {
            params.target.setImageResource(placeholderId)
        }

        sendMessage(K_LOAD, params)
    }

    fun destroy() {
        if (log.isDebugEnabled) {
            log.debug("FREE HANDLER")
        }

        // https://stackoverflow.com/questions/5883635/how-to-remove-all-callbacks-from-a-handler
        handler.removeCallbacksAndMessages(null)
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // HANDLER
    //
    ////////////////////////////////////////////////////////////////////////////////////

    private fun processLoad(params: ImageLoaderParams) {
        val bitmap = memCache.get(params.url)
        if (bitmap != null) {
            if (log.isDebugEnabled) {
                log.debug("FOUND MEM CACHE (${params.url}" )
            }

            activity.runOnUiThread { params.target.setImageBitmap(bitmap) }
        } else {
            processNetwork(params)
        }
    }

    private fun processNetwork(params: ImageLoaderParams) {
        if (okhttp == null) {
            okhttp = okhttp(params)
        }

        val request = Request.Builder().url(params.url)

        okhttp?.run {
            newCall(request.build()).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    e?.run {
                        printStackTrace()
                        log.error("ERROR: ${message}")
                    }

                    params.listener?.invoke(false, null)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    response?.run {
                        if (!isSuccessful) {
                            params.listener?.invoke(false, null)
                            log.error("ERROR: RESPONSE CODE ${code()}")

                            return
                        }

                        val body = body()
                        val bmp  = decodeBitmap(body.source())

                        memCache.set(params.url, bmp)
                        activity.runOnUiThread { params.target.setImageBitmap(bmp) }

                        params.listener?.invoke(true, bmp)
                    } ?: params.listener?.invoke(false, null)
                }
            })
        }
    }

    private fun okhttp(params: ImageLoaderParams): OkHttpClient {
        val diskCache = DiskCache(params.context)
        val logger  = HttpLoggingInterceptor().setLevel(AppConfig.OKHTTP_LOGLEVEL)
        val builder = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(logger)
                .cache(Cache(diskCache.diskCacheFp, diskCache.cacheSize()))

        return builder.build()
    }

    private fun decodeBitmap(src: Source): Bitmap {
        val option = BitmapFactory.Options()
        val ioBuff = Okio.buffer(src)
        val bitmap = BitmapFactory.decodeStream(ioBuff.inputStream(), null, option)

        return bitmap
    }
}

class ImageLoaderParams {
    lateinit var context: Context
    lateinit var url : String
    lateinit var target: ImageView

    val listener: ((Boolean, Bitmap?) -> Unit?)? = null
}

class DiskCache(val context: Context){
    companion object {
        private val log = LoggerFactory.getLogger(DiskCache::class.java)

        const val DISK_CACHE_PATH = "image-loader-cache"
        const val MIN_CACHE_SIZE  = 5f * 1024f * 1024f      // 5MB
        const val MAX_CACHE_SIZE  = MIN_CACHE_SIZE * 20f    // 100MB
    }

    val diskCacheFp  = cacheDir()
//    val cacheSize    = cacheSize()
//    val diskLruCache = DiskLruCache.create(FileSystem.SYSTEM, diskCacheFp,
//            201804, 2, cacheSize)

    fun cacheSize(): Long {
        // 디스크 양에서
        val blockSize = BkSystem.blockSize(diskCacheFp)

        // 두 값중 작은 값을 반환 하고
        val min = Math.min(blockSize / 50f, MAX_CACHE_SIZE)

        // 반환된 값 중 큰수를 반환 한다.
        val cacheSize = Math.max(min, MIN_CACHE_SIZE).toLong()

        if (log.isDebugEnabled) {
            log.debug("DISK CACHE SIZE : ${Formatter.formatFileSize(context, cacheSize)}")
        }

        // 결국 최소는 디스크 크기에 최대는 MAX 크기에 영향
        return cacheSize
    }

    private fun cacheDir(): File {
        val fp = File(context.applicationContext.cacheDir, DISK_CACHE_PATH)
        if (!fp.exists()) {
            fp.mkdirs()
        }

        return fp
    }
}

class MemoryCache(val context: Context) {
    companion object {
        private val log = LoggerFactory.getLogger(MemoryCache::class.java)
        private val MEM_CACHE_RATIO = 5 // 전체 메몰에서 1/5만큼 캐시 할당
    }

    val cache = ImageLoaderLruCache(cacheSize())

    fun get(key: String) : Bitmap? = cache.get(key)?.run { bitmap }

    fun set(key: String, bitmap: Bitmap) {
        val size = BitmapCompat.getAllocationByteCount(bitmap)

        if (size > cache.maxSize()) {
            cache.remove(key)
            return
        }

        cache.put(key, BitmapInfo(bitmap, size))
    }

    private fun cacheSize(): Int {
        val manager   = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val largeHeap = (context.applicationInfo.flags and FLAG_LARGE_HEAP) != 0
        val memClass  = if (largeHeap) manager.largeMemoryClass else manager.memoryClass
        val cacheSize = 1024L * 1024L * memClass / MEM_CACHE_RATIO

        if (log.isDebugEnabled) {
            log.debug("MEM CACHE SIZE : ${Formatter.formatFileSize(context, cacheSize)}")
        }

        return cacheSize.toInt()
    }
}

class ImageLoaderLruCache(maxBytes: Int = 1) : LruCache<String, BitmapInfo>(maxBytes) {
    override fun sizeOf(key: String?, value: BitmapInfo?) = value?.size ?: 0
}

data class BitmapInfo(val bitmap: Bitmap, val size: Int)