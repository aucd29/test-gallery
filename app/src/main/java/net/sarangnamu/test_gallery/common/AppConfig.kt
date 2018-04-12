package net.sarangnamu.test_gallery.common

import net.sarangnamu.test_gallery.model.GettyImageInfo


/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class AppConfig {
    companion object {
        const val DUMY_MODE             = true
        const val GRID_HORIZONTAL_SIZE  = 4
        const val NETWORK_TIMEOUT       = 10000L
    }

    class Dumy {
        companion object {
            val imageList = arrayListOf(
                GettyImageInfo("/Images/Thumbnails/1343/134306.jpg", "Corfu Picnic"),
                GettyImageInfo("/Images/Thumbnails/1361/136166.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134370.jpg", "Cortina d'Ampezzo"),
                GettyImageInfo("/Images/Thumbnails/1343/134374.jpg", "Cortina d'Ampezzo"))
        }
    }
}