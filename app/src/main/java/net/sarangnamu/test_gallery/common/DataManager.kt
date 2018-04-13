package net.sarangnamu.test_gallery.common

import android.app.Activity
import net.sarangnamu.common.async
import net.sarangnamu.test_gallery.getty.GettyImageInfo
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 */
class DataManager private constructor() {
    private object Holder { val INSTANCE = DataManager() }

    companion object {
        private val log = LoggerFactory.getLogger(DataManager::class.java)
        val  get: DataManager by lazy { Holder.INSTANCE }
    }

    var data: IData<GettyImageInfo>? = null
    var imageList = arrayListOf<GettyImageInfo>()

    fun init(html: String) {
        data?.init(html)
    }

    fun load(activity: Activity, listener: (Boolean) -> (Unit)) {
        activity.async ({
            try {
                data?.next()
                imageList = data?.list()!!

                true
            } catch (e: Exception) {
                e.printStackTrace()
                log.error("ERROR: ${e.message}")

                false
            }
        }, listener)
    }
}
