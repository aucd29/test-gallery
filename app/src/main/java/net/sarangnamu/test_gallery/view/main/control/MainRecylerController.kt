package net.sarangnamu.test_gallery.view.main.control

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.main_grid_row.view.*
import net.sarangnamu.common.V7Adapter
import net.sarangnamu.test_gallery.getty.GettyConfig
import net.sarangnamu.test_gallery.imageloader.ImageLoader
import net.sarangnamu.test_gallery.imageloader.ImageLoaderParams
import net.sarangnamu.test_gallery.model.GettyImageInfo

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 12.. <p/>
 */

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image   = itemView.main_image
    val caption = itemView.main_caption
}

class MainAdapter(context: Context, @LayoutRes id: Int, dataList: ArrayList<GettyImageInfo>)
    : V7Adapter<GettyImageInfo, MainViewHolder>(context, id, dataList) {

    val loader = ImageLoader(mContext as Activity)

    override fun bindView(holder: MainViewHolder, data: GettyImageInfo) {
        holder.caption.text = data.caption

        loader.load(ImageLoaderParams().apply {
            this.context = mContext
            this.url     = GettyConfig.BASE_URL + data.path
            this.target  = holder.image
        })
    }
}