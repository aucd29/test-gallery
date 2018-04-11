package net.sarangnamu.test_gallery.model.getty

import android.os.AsyncTask
import net.sarangnamu.test_gallery.model.DataManager
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class GettyParsingTask(val manager: DataManager, val rawData: String, val listener: (Boolean) -> (Unit))
    : AsyncTask<Void, Void, Boolean>() {
    companion object {
        private val log = LoggerFactory.getLogger(GettyParsingTask::class.java)
    }

    private var parser = GettyParser()

    override fun doInBackground(vararg params: Void?): Boolean {
        try {
            parser.loadXml("<root>${rawData}</root>")

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("ERROR: ${e.message}")

            return false
        }
    }

    override fun onPostExecute(result: Boolean) {
        if (result) {
            manager.imageList = parser.imageList
        }

        listener.invoke(result)
    }
}
