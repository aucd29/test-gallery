package net.sarangnamu.test_gallery.common

import android.app.Activity
import android.support.annotation.StringRes
import net.sarangnamu.common.DialogParam
import net.sarangnamu.common.dialog
import net.sarangnamu.common.string
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.getty.GettyConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
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

    fun load(activity: Activity?, listener: () -> Unit) {
        val request  = Request.Builder().url(GettyConfig.URL)

        okhttp().newCall(request.get().build()).enqueue(NetworkCallback(activity, listener))
    }

    private fun okhttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(logger).build()
    }

    class NetworkCallback(val activity: Activity?, val listener: () -> Unit) : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            e?.run {
                printStackTrace()
                log.error("ERROR: ${message}")
            }

            alert(activity, R.string.network_occur_error)
        }

        override fun onResponse(call: Call?, response: Response?) {
            if (log.isDebugEnabled) {
                log.debug("RESPONSE CODE = ${response?.code()}")
            }

            response?.run {
                if (code() >= 400) {
                    alert(activity, R.string.network_occur_error)
                    return
                }

                DataManager.get.init(body().string(), { result ->
                    if (result) {
                        // 완료 했으면 메인 화면으로 이동
                        listener.invoke()
                    } else {
                        log.error("ERROR: PARSING ")

                        alert(activity, R.string.datamanager_unknown_error)
                    }
                })
            } ?: alert(activity, R.string.splash_response_error)
        }

        fun alert(activity: Activity?, @StringRes msgId: Int) {
            activity?.run {
                dialog(DialogParam().apply {
                    okCancel()

                    title    = string(R.string.button_alert)
                    message  = string(msgId)
                    positive = { finishAffinity() }
                })
            }
        }
    }
}