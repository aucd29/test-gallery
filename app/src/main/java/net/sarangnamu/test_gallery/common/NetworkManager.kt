package net.sarangnamu.test_gallery.common

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 * * gettyimages
 *  API PAGE = http://developers.gettyimages.com/
 *  REGISTER = https://developer.gettyimages.com/member/register
 *  API = https://api.gettyimages.com/v3/search/images?fields=id,caption,thumb,referral_destinations&sort_order=best&phrase=test
 *
 *  github
 *  https://github.com/gettyimages/gettyimages-api/blob/master/code-samples/php/image-search.php
 *
 * okhttp
 *  : http://square.github.io/okhttp/
 * okhttp-logging-interceptor
 *  : https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
 */

class NetworkManager private constructor() {
    private object Holder { val INSTANCE = NetworkManager() }

    companion object {
        val get: NetworkManager by lazy { Holder.INSTANCE }
        private val log = LoggerFactory.getLogger(NetworkManager::class.java)
    }

    fun body(url: String, listener: (Boolean, ResponseBody?) -> Unit) {
        val request  = Request.Builder().url(url)

        okhttp().newCall(request.get().build()).enqueue(NetworkCallback(listener))
    }

    fun okhttp(cacheFp: File? = null, cacheSize: Long = 0): OkHttpClient {
        val logger  = HttpLoggingInterceptor().setLevel(AppConfig.OKHTTP_LOGLEVEL)
        val builder = OkHttpClient.Builder()
                        .retryOnConnectionFailure(false)
                        .readTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                        .connectTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                        .followRedirects(true)
                        .followSslRedirects(true)
                        .addInterceptor(logger)

        if (cacheFp != null) {
            builder.cache(Cache(cacheFp, cacheSize))
        }

        return builder.build()
    }

    class NetworkCallback(val listener: (Boolean, ResponseBody?) -> Unit) : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            e?.run {
                printStackTrace()
                log.error("ERROR: ${message}")
            }

            listener.invoke(false, null)
        }

        override fun onResponse(call: Call?, response: Response) {
            if (!response.isSuccessful) {
                log.error("ERROR: RESPONSE")
                listener.invoke(false, null)

                return
            }

            listener.invoke(true, response.body())
        }
    }
}