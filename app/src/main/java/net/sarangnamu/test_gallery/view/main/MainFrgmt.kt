package net.sarangnamu.test_gallery.view.main

import kotlinx.android.synthetic.main.main_layout.view.*
import net.sarangnamu.common.FrgmtBase
import net.sarangnamu.common.gridLayout
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.common.AppConfig
import net.sarangnamu.test_gallery.common.DataManager
import net.sarangnamu.test_gallery.view.main.control.MainAdapter
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class MainFrgmt : FrgmtBase() {
    companion object {
        private val log = LoggerFactory.getLogger(MainFrgmt::class.java)
    }

    override fun layoutId() = R.layout.main_layout

    override fun initLayout() {
        if (log.isDebugEnabled) {
            log.debug("INIT RECYCLER VIEW")
            log.debug("DATA SIZE : ${DataManager.get.imageList.size}")
        }

        base.main_recycler.run {
            gridLayout(AppConfig.GRID_HORIZONTAL_SIZE)

            adapter = MainAdapter(activity!!
                    , R.layout.main_grid_row
                    , DataManager.get.imageList)
        }
    }
}
