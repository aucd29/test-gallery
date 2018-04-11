package net.sarangnamu.test_gallery.view.splash

import android.support.annotation.StringRes
import kotlinx.android.synthetic.main.splash_layout.view.*
import net.sarangnamu.common.*
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.model.DataManager
import net.sarangnamu.test_gallery.network.NetworkManager
import net.sarangnamu.test_gallery.view.main.MainFrgmt
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.io.IOException

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

        upAndDownIcon()
        auth()
    }

    private fun auth() {
        if (!activity!!.isNetworkConnected()) {
            log.error("ERROR: NETWORK DISCONNECT")

            alert(R.string.network_occur_error)
            return
        }

        NetworkManager.instance.load(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                alert(R.string.network_occur_error)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (log.isDebugEnabled) {
                    log.debug("RESPONSE CODE = ${response?.code()}")
                }

                response?.run {
                    if (code()!! >= 400) {
                        alert(R.string.network_occur_error)
                        return
                    }

                    DataManager.instance.load(body().string())

                    // 완료 했으면 메인 화면으로 이동
                    showMainFragment()
            } ?: alert(R.string.splash_response_error)
            }
        })
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
            log.debug("SHOW MAIN FRAGMENT")
        }

        activity?.supportFragmentManager?.replace(R.id.root_layout, MainFrgmt::class.java)
    }
}