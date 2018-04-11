package net.sarangnamu.test_gallery.view.splash

import kotlinx.android.synthetic.main.splash_layout.view.*
import net.sarangnamu.common.FrgmtBase
import net.sarangnamu.common.dpToPixel
import net.sarangnamu.test_gallery.R
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class SplashFrgmt : FrgmtBase() {
    companion object {
        private val log = LoggerFactory.getLogger(SplashFrgmt::class.java)
        private val ANI_DURATION: Long = 500
        private val ANI_DISTANCE       = 10f.dpToPixel()
    }

    override fun layoutId(): Int = R.layout.splash_layout

    override fun initLayout() {
        if (log.isTraceEnabled()) {
            log.trace("SHOW SPLASH")
        }

        upAndDownIcon()
    }

    private fun upAndDownIcon() {
        if (log.isTraceEnabled()) {
            log.trace("START ICON ANIMATION")
        }

        animateIcon(ANI_DISTANCE, Runnable {
            animateIcon(ANI_DISTANCE * -1f)
        })
    }

    private fun animateIcon(transition: Float, listener: Runnable? = null) {
        base.splash_icon.animate().translationY(transition).setDuration(ANI_DURATION)
            .withEndAction(listener).start()
    }
}