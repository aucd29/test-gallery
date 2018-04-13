package net.sarangnamu.test_gallery.view.main

import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.main_layout.view.*
import net.sarangnamu.common.gridLayout
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.common.AppConfig
import net.sarangnamu.test_gallery.common.DataProxy
import net.sarangnamu.test_gallery.view.AppFrgmtBase
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class MainFrgmt : AppFrgmtBase() {
    companion object {
        private val log = LoggerFactory.getLogger(MainFrgmt::class.java)
    }

    override fun layoutId() = R.layout.main_layout

    override fun initLayout() {
        val total = DataProxy.get.total()
        val list  = DataProxy.get.list()

        if (log.isDebugEnabled) {
            log.debug("INIT RECYCLER VIEW")
            log.debug("TOTAL SIZE : $total")
            log.debug("LIST SIZE  : ${list?.size}")
        }

        if (list?.size == 0) {
            log.error("ERROR: LIST DATA PROBLEM")
            error(R.string.unknown_error)

            return
        }

        base.main_recycler.run {
            gridLayout(AppConfig.GRID_X_SIZE).spanSizeLookup =
                object: GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == list?.run { size } ?: 0) AppConfig.GRID_X_SIZE else 1
                    }
                }

            adapter = MainAdapter(activity!! , total , R.layout.main_grid_row , list!!)
//            adapter.setHasStableIds(true)
        }
    }
}
