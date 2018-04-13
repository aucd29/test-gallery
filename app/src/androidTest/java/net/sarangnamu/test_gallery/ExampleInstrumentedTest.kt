package net.sarangnamu.test_gallery

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import net.sarangnamu.test_gallery.common.AppConfig
import net.sarangnamu.test_gallery.common.DataProxy
import net.sarangnamu.test_gallery.common.NetworkManager
import net.sarangnamu.test_gallery.getty.GettyConfig
import net.sarangnamu.test_gallery.getty.GettyParser

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

    @Test
    fun testNetwork() {
        NetworkManager.get.body("http://sarangnamu.net", { res, body ->
            assertEquals(true, res)
            assertNotEquals(null, body?.string())
        })
    }

    @Test
    fun testGetty() {
        NetworkManager.get.body(GettyConfig.LIST_URL, { res, body ->
            assertEquals(true, res)

            val html = body?.string()
            assertNotEquals(null, html)

            val fpos = html?.indexOf("<!-- REPEATER -->")
            val lpos = html?.lastIndexOf("<!-- REPEATER ENDS -->")

            assertNotEquals(-1, fpos)
            assertNotEquals(-1, lpos)
        })
    }

    @Test
    fun testDataProxy() {
        val appContext = InstrumentationRegistry.getTargetContext()

        NetworkManager.get.body(GettyConfig.LIST_URL, { res, body ->
            DataProxy.get.run {
                data = GettyParser()
                data?.limit(6)

                init(body?.string()!!)
                load(appContext, { assertEquals(true, it) })
            }
        })
    }
}
