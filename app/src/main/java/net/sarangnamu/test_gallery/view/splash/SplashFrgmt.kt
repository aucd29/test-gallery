package net.sarangnamu.test_gallery.view.splash

import android.support.annotation.StringRes
import kotlinx.android.synthetic.main.splash_layout.view.*
import net.sarangnamu.common.*
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.common.AppConfig
import net.sarangnamu.test_gallery.common.DataManager
import net.sarangnamu.test_gallery.common.NetworkManager
import net.sarangnamu.test_gallery.view.main.MainFrgmt
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class SplashFrgmt : FrgmtBase() {
    companion object {
        private val log = LoggerFactory.getLogger(SplashFrgmt::class.java)
        private val ANI_DURATION = 500L
        private val ANI_DISTANCE = 10f.dpToPixel()
    }

    override fun layoutId(): Int = R.layout.splash_layout

    override fun initLayout() {
        if (log.isTraceEnabled()) {
            log.trace("SHOW SPLASH")
        }

        shakeIcon()
        loadData()
    }

    private fun loadData() {
        if (!activity!!.isNetworkConnected()) {
            log.error("ERROR: NETWORK DISCONNECT")

            alert(R.string.network_occur_error)
            return
        }

        if (AppConfig.DUMY_MODE) {
            // 더미 데이터 적용
            DataManager.get.imageList = AppConfig.Dumy.imageList
            base.postDelayed({ showMainFragment() }, 1000)

            return
        }

        NetworkManager.get.load(activity, {
            if (it) {
                // 완료 했으면 메인 화면으로 이동
                showMainFragment()
            } else {
                alert(R.string.network_occur_error)
            }
        })
    }

    private fun shakeIcon() {
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

    private fun alert(@StringRes msgId: Int) {
        activity?.run {
            dialog(DialogParam().apply {
                okCancel()

                title    = string(R.string.button_alert)
                message  = string(msgId)
                positive = { finishAffinity() }
            })
        }
    }

    private fun showMainFragment() {
        if (log.isDebugEnabled) {
            log.debug("REPLACE MainFrgmt")
        }

        activity?.supportFragmentManager?.replace(R.id.root_layout, MainFrgmt::class.java)
    }
}