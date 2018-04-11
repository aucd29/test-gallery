package net.sarangnamu.test_gallery.network

import net.sarangnamu.test_gallery.model.AppConfig
import net.sarangnamu.test_gallery.model.getty.GettyConfig
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 * * gettyimages
 *  API PAGE = http://developers.gettyimages.com/
 *  REGISTER = https://developer.gettyimages.com/member/register
 *  API = https://api.gettyimages.com/v3/search/images?fields=id,title,thumb,referral_destinations&sort_order=best&phrase=test
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
        val instance: NetworkManager by lazy { Holder.INSTANCE }
    }

    fun load(callback: Callback) {
        val request  = Request.Builder().url(GettyConfig.URL)

        okhttp().newCall(request.get().build()).enqueue(callback)
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
}