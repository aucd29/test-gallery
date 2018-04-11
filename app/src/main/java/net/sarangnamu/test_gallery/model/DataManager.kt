package net.sarangnamu.test_gallery.model

import net.sarangnamu.test_gallery.model.getty.GettyConfig
import net.sarangnamu.test_gallery.model.getty.GettyParser
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *

 */

class DataManager private constructor() {
    private object Holder { val INSTANCE = DataManager() }

    companion object {
        private val log = LoggerFactory.getLogger(DataManager::class.java)
        val instance: DataManager by lazy { Holder.INSTANCE }
    }

    private var parser = GettyParser()

    fun load(html: String) {
        val fpos = html.indexOf("<!-- REPEATER -->")
        val lpos = html.lastIndexOf("<!-- REPEATER ENDS -->")
        
        if (fpos == -1 || lpos == -1) {
            log.error("ERROR: fpos = $fpos, lpos = $lpos")
            
            return 
        }

        val rawData = html.substring(fpos, lpos)
        parser.loadXml("<root>" + rawData + "</root>")
    }
}
