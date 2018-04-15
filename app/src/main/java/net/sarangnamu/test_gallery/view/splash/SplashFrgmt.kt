package net.sarangnamu.test_gallery.view.splash

import kotlinx.android.synthetic.main.splash_layout.view.*
import net.sarangnamu.common.*
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.common.AppConfig
import net.sarangnamu.test_gallery.common.DataProxy
import net.sarangnamu.test_gallery.common.Network
import net.sarangnamu.test_gallery.getty.GettyConfig
import net.sarangnamu.test_gallery.getty.GettyParser
import net.sarangnamu.test_gallery.view.AppFrgmtBase
import net.sarangnamu.test_gallery.view.main.MainFrgmt
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class SplashFrgmt : AppFrgmtBase() {
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

            error(R.string.network_occur_error)
            return
        }

        if (AppConfig.DUMY_MODE) {
            // 더미 모드시에는 미리 정의해둔 데이터 적용
            DataProxy.get.run {
                data = GettyParser()
                data?.limit(AppConfig.GRID_X_SIZE * AppConfig.GRID_Y_SIZE)
                total(AppConfig.Dumy.imageList.size)
                list(AppConfig.Dumy.imageList)
            }

            base.postDelayed({ showMainFragment() }, 1000)

            return
        }

        // 서버에서 html 을 읽어다가 data proxy 에 전달
        Network.get.body(GettyConfig.LIST_URL, { res, body ->
            if (!res) {
                error(R.string.network_occur_error)
            } else {
                body?.run {
                    DataProxy.get.run {
                        data = GettyParser()
                        data?.limit(AppConfig.GRID_X_SIZE * AppConfig.GRID_Y_SIZE)

                        init(string())
                        load(activity!!, {
                            when (it) {
                                true -> showMainFragment()
                                else -> error(R.string.dataproxy_process_error)
                            }
                        })
                    }
                } ?: error(R.string.network_occur_error)
            }
        })
    }

    private fun shakeIcon() {
        if (log.isTraceEnabled()) {
            log.trace("START ICON ANIMATION")
        }

        animateIcon(ANI_DISTANCE, Runnable {
            animateIcon(ANI_DISTANCE * -1f * 2, Runnable {
                animateIcon(ANI_DISTANCE)
            })
        })
    }

    private fun animateIcon(transition: Float, listener: Runnable? = null) {
        base.splash_icon.animate().translationY(transition).setDuration(ANI_DURATION)
            .withEndAction(listener).start()
    }

    private fun showMainFragment() {
        if (log.isDebugEnabled) {
            log.debug("REPLACE MainFrgmt")
        }

        activity?.run {
            supportFragmentManager.replace(R.id.root_layout, MainFrgmt::class.java)
        } ?: error(R.string.unknown_error)
    }
}