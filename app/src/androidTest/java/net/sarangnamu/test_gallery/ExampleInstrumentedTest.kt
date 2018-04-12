package net.sarangnamu.test_gallery

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import net.sarangnamu.test_gallery.common.DataManager
import net.sarangnamu.test_gallery.common.NetworkManager

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("net.sarangnamu.test_gallery", appContext.packageName)
    }

//    @Test
//    fun testNetworkManager() {
////        NetworkManager.get.load()
//    }

    @Test
    fun testDataManager() {
        val html = "<root><div class=\"gallery-item-group lastitemrepeater\">\n" +
                "    <a href=\"/Picture-Library/Image.aspx?id=7824\"><img src=\"/Images/Thumbnails/1552/155296.jpg\" class=\"picture\"/></a>\n" +
                "\n" +
                "    <div class=\"gallery-item-caption\">\n" +
                "    <p><a href=\"/Picture-Library/Image.aspx?id=7824\">We Know Our Place</a></p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"gallery-item-group exitemrepeater\">\n" +
                "    <a href=\"/Picture-Library/Image.aspx?id=2455\"><img src=\"/Images/Thumbnails/1343/134346.jpg\" class=\"picture\"/></a>\n" +
                "\n" +
                "    <div class=\"gallery-item-caption\">\n" +
                "    <p><a href=\"/Picture-Library/Image.aspx?id=2455\">Aerial Miami Beach</a></p>\n" +
                "    </div>\n" +
                "</div></root>"

        DataManager.get.init(html, { assertEquals(true, it) })
    }
}
