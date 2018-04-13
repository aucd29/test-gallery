package net.sarangnamu.test_gallery.view

import android.support.annotation.StringRes
import net.sarangnamu.common.DialogParam
import net.sarangnamu.common.FrgmtBase
import net.sarangnamu.common.dialog
import net.sarangnamu.common.string
import net.sarangnamu.test_gallery.R

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 12.. <p/>
 */

abstract class AppFrgmtBase : FrgmtBase() {
    protected fun error(@StringRes msgId: Int) {
        activity?.run {
            dialog(DialogParam().apply {
                title    = string(R.string.title_error)
                message  = string(msgId)
                positive = { finishAffinity() }
            })
        }
    }
}