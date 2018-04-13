package net.sarangnamu.test_gallery.common

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

class Network private constructor() {
    private object Holder { val INSTANCE = Network() }

    companion object {
        private val log = LoggerFactory.getLogger(Network::class.java)

        val get: Network by lazy { Holder.INSTANCE }
    }

    fun body(url: String, listener: (Boolean, ResponseBody?) -> Unit) {
        val request = Request.Builder().url(url)

        okhttp().newCall(request.get().build()).enqueue(NetworkCallback(listener))
    }

    private fun okhttp(): OkHttpClient {
        val logger  = HttpLoggingInterceptor().setLevel(AppConfig.OKHTTP_LOGLEVEL)
        val builder = OkHttpClient.Builder()
                        .retryOnConnectionFailure(false)
                        .readTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                        .connectTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                        .addInterceptor(logger)
                        .cache(null)

        return builder.build()
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // NetworkCallback
    //
    ////////////////////////////////////////////////////////////////////////////////////

    private class NetworkCallback(val listener: (Boolean, ResponseBody?) -> Unit) : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            e?.run {
                printStackTrace()
                log.error("ERROR: $message")
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