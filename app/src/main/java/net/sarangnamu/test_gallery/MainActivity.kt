package net.sarangnamu.test_gallery

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.sarangnamu.common.AppTerminator
import net.sarangnamu.common.add
import net.sarangnamu.test_gallery.view.splash.SplashFrgmt
import org.slf4j.LoggerFactory

class MainActivity : AppCompatActivity() {
    companion object {
        private val log = LoggerFactory.getLogger(MainActivity::class.java)
    }

    private var appTerminator: AppTerminator? = null

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
        appTerminator?.onBackPressed() ?: super.onBackPressed()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    private fun init() {
        appTerminator = AppTerminator(this@MainActivity, root_layout)
    }

    private fun initFragment() {
        if (log.isTraceEnabled()) {
            log.trace("INIT FRGMT")
        }

        supportFragmentManager.add(R.id.root_layout, SplashFrgmt::class.java)
    }
}
