package net.sarangnamu.test_gallery.common

import net.sarangnamu.test_gallery.BuildConfig
import net.sarangnamu.test_gallery.getty.GettyImageInfo
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class AppConfig {
    companion object {
        const val DUMY_MODE       = false
        const val GRID_X_SIZE     = 3
        const val GRID_Y_SIZE     = 7
        const val NETWORK_TIMEOUT = 10000L

        val OKHTTP_LOGLEVEL             = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                                          else HttpLoggingInterceptor.Level.NONE
    }

    class Dumy {
        companion object {
            val imageList = arrayListOf(
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"))
        }
    }
}