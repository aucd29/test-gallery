package net.sarangnamu.common

import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 13.. <p/>
 */

class TimeLog(val message: String) {
    companion object {
        private val log = LoggerFactory.getLogger(TimeLog::class.java)
    }
    
    private var startTime = System.currentTimeMillis()

    init {
        if (log.isDebugEnabled) {
            log.debug("[TIMELOG] $message START")
        }
    }

    fun finish() {
        if (log.isDebugEnabled) {
            val time = System.currentTimeMillis() - startTime
            log.debug("[TIMELOG] $message FINISH ($time ms)")
        }
    }
}