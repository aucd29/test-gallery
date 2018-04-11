package net.sarangnamu.test_gallery.view.main

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.main_grid_row.view.*
import kotlinx.android.synthetic.main.main_layout.view.*
import net.sarangnamu.common.FrgmtBase
import net.sarangnamu.common.V7Adapter
import net.sarangnamu.common.gridLayout
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.model.AppConfig
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
        // TODO
        // 일단 데이터는 splash 에서 가져오는걸로 하고 ..
        val dataList = arrayListOf<ImageData>()


        if (log.isDebugEnabled) {
            log.debug("DATA LIST SIZE : ${dataList.size}")
        }

        view?.main_recycler?.run {
            gridLayout(AppConfig.GRID_HORIZONTAL_SIZE)

            if (log.isDebugEnabled) {
                log.debug("ACTIVITY = $activity")
            }

            adapter = MainGridAdapter(activity!!
                    , R.layout.main_grid_row
                    , dataList)
        }
    }
}

class ImageData {

}

class MainGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.main_image
}

class MainGridAdapter(override var context: Context,
                      @LayoutRes override var id: Int,
                      override var dataList: ArrayList<ImageData>)
        : V7Adapter<ImageData, MainGridViewHolder>(context, id, dataList) {

    override fun bindData(holder: MainGridViewHolder, data: ImageData) {
        // TODO

    }
}