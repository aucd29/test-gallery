package net.sarangnamu.test_gallery

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.sarangnamu.common.AppCloser
import net.sarangnamu.common.add
import net.sarangnamu.common.replace
import net.sarangnamu.test_gallery.view.main.MainFrgmt
import net.sarangnamu.test_gallery.view.splash.SplashFrgmt
import org.slf4j.LoggerFactory

class MainActivity : AppCompatActivity() {
    companion object {
        private val log = LoggerFactory.getLogger(MainActivity::class.java)
    }

    private var appCloser: AppCloser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (log.isTraceEnabled()) {
            log.trace("START APP")
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        if (savedInstanceState == null) {
            initFragment()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        appCloser?.onBackPressed() ?: super.onBackPressed()
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // USER METHOD
    //
    ////////////////////////////////////////////////////////////////////////////////////

    private fun init() {
        appCloser = AppCloser(this@MainActivity, root_layout)
    }

    private fun initFragment() {
        if (log.isTraceEnabled()) {
            log.trace("INIT FRGMT")
        }

        supportFragmentManager.add(R.id.root_layout, SplashFrgmt::class.java)
    }
}
