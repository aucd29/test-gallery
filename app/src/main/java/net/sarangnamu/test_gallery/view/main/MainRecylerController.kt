package net.sarangnamu.test_gallery.view.main

import android.app.Activity
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_grid_row.view.*
import kotlinx.android.synthetic.main.main_grid_row_more.view.*
import net.sarangnamu.common.DialogParam
import net.sarangnamu.common.V7Adapter
import net.sarangnamu.common.dialog
import net.sarangnamu.common.isNetworkConnected
import net.sarangnamu.test_gallery.R
import net.sarangnamu.test_gallery.common.DataProxy
import net.sarangnamu.test_gallery.getty.GettyConfig
import net.sarangnamu.test_gallery.getty.GettyImageInfo
import net.sarangnamu.test_gallery.imageloader.ImageLoader
import net.sarangnamu.test_gallery.imageloader.ImageLoaderParams
import net.sarangnamu.test_gallery.view.main.MainAdapter.Companion.T_DEFAULT
import net.sarangnamu.test_gallery.view.main.MainAdapter.Companion.T_FOOTER
import net.sarangnamu.test_gallery.view.splash.SplashFrgmt
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 12.. <p/>
 */

////////////////////////////////////////////////////////////////////////////////////
//
// VIEW HOLDER
//
////////////////////////////////////////////////////////////////////////////////////

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image   = itemView.main_image
    val caption = itemView.main_caption
}

class MainFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val more     = itemView.main_more
    val progress = itemView.main_progress

    fun showMore() {
        more.visibility     = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    fun showProgress() {
        more.visibility     = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// MainAdapterParams
//
////////////////////////////////////////////////////////////////////////////////////

data class MainAdapterParams(val activity: Activity,
                             val total: Int,
                             val loader: ImageLoader)

////////////////////////////////////////////////////////////////////////////////////
//
// ADAPTER
//
////////////////////////////////////////////////////////////////////////////////////

class MainAdapter(val params: MainAdapterParams,
                  @LayoutRes id: Int, dataList: ArrayList<GettyImageInfo>)
    : V7Adapter<GettyImageInfo, RecyclerView.ViewHolder>(params.activity, id, dataList)
    , View.OnClickListener {

    companion object {
        private val log = LoggerFactory.getLogger(MainAdapter::class.java)

        const val T_DEFAULT = 0
        const val T_FOOTER  = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            T_FOOTER -> MainFooterViewHolder(inflate(context, R.layout.main_grid_row_more, null))
            else     -> MainViewHolder(inflate(context, id, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataList.size > position) {
            super.onBindViewHolder(holder, position)
        } else {
            bindView(holder, null)
        }
    }

    override fun bindView(holder: RecyclerView.ViewHolder, data: GettyImageInfo?) {
        when (holder) {
            is MainViewHolder -> {
                if (data == null) {
                    log.error("ERROR: bind data == null")
                    
                    return 
                }
                
                holder.caption.text = data.caption
                params.loader.load(ImageLoaderParams().apply {
                    this.context = params.activity
                    this.url     = GettyConfig.BASE_URL + data.path
                    this.target  = holder.image
                })
            }

            is MainFooterViewHolder -> {
                holder.more.setOnClickListener(this)
                holder.more.tag = holder
            }
        }
    }

    override fun getItemCount(): Int {
        if (dataList.size == params.total) {
            return dataList.size
        }

        return (dataList.size + 1)
    }

    override fun getItemViewType(position: Int): Int =
        if (position == dataList.size) T_FOOTER else T_DEFAULT

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // View.OnClickListener
    //
    ////////////////////////////////////////////////////////////////////////////////////

    override fun onClick(v: View) {
        if (!params.activity.isNetworkConnected()) {
            log.error("ERROR: NETWORK DISCONNECT")

            alert(R.string.network_occur_error)
            return
        }

        val holder = v.tag as MainFooterViewHolder

        // 다음 페이지 호출
        holder.showProgress()

        DataProxy.get.load(params.activity, {
            if (it) {
                holder.showMore()
                DataProxy.get.list()?.run { invalidate(this) }
                        ?: alert(R.string.unknown_error)
            } else {
                alert(R.string.dataproxy_process_error)
            }
        })
    }

    private fun alert(@StringRes resid: Int) {
        params.activity.dialog(DialogParam(resid, R.string.title_error)).show()
    }
}