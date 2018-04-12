/*
 * Copyright 2016 Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE", "unused")
package net.sarangnamu.common

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.FrameLayout

// https://kotlinlang.org/docs/tutorials/android-plugin.html
import kotlinx.android.synthetic.main.dlg_web.*
import kotlinx.android.synthetic.main.dlg_web.view.*
import net.sarangnamu.test_gallery.R

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 *
 * 그냥 anko 를 쓸까? -_ -;;
 */

class DialogParam(@StringRes messageId: Int = 0, @StringRes titleId: Int = 0): DlgParam(messageId) {
    var title: String? = null
    var positive: ((DialogInterface) -> Unit)? = null
    var negative: ((DialogInterface) -> Unit)? = null
    var hideButton: Boolean = false

    init {
        if (titleId != 0) {
            title = BkApp.context().string(titleId)
        }
    }

    fun yesNo() {
        positiveText = android.R.string.yes
        negativeText = android.R.string.no
    }

    fun okCancel() {
        positiveText = android.R.string.ok
        negativeText = android.R.string.cancel
    }

    @StringRes var positiveText = android.R.string.ok
    @StringRes var negativeText = android.R.string.cancel
}

open class LoadingParam(@StringRes message: Int = 0): DlgParam(message) {
    var style_horizontal = false
}

open class DlgParam(@StringRes messageId: Int = 0) {
    var message: String? = null
    @LayoutRes var resid: Int = 0

    init {
        if (messageId != 0) {
            message = BkApp.context().string(messageId)
        }
    }
}

inline fun Fragment.dialog(params: DialogParam): AlertDialog.Builder? {
    return activity?.dialog(params)
}

inline fun Activity.dialog(params: DialogParam): AlertDialog.Builder {
    val bd = AlertDialog.Builder(this)

    with (params) { with (bd) {
        if (!hideButton && resid == 0) {
            setPositiveButton(positiveText, { d, i -> d.dismiss(); positive?.invoke(d) })
            negative?.let { setNegativeButton(negativeText, { d, i -> d.dismiss(); it(d) }) }
        }

        title?.let { setTitle(it) }
        message?.let { setMessage(it) }

        if (resid != 0) {
            setView(resid)
        }
    } }

    return bd
}

inline fun Activity.dialog(params: DialogParam, killTimeMillis: Long) {
    val dlg = dialog(params).show()
    window.decorView.postDelayed({ dlg.dismiss() }, killTimeMillis)
}

inline fun Fragment.loading(params: LoadingParam): ProgressDialog? {
    return activity?.loading(params)
}

inline fun Activity.loading(params: LoadingParam): ProgressDialog {
    val bd = ProgressDialog(this)

    if (params.style_horizontal) {
        bd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    } else {
        bd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    with (params) { with (bd) {
        message?.let { setMessage(it) }
        if (resid != 0) {
            setView(layoutInflater.inflate(resid, null))
        }
    } }

    return bd
}

inline fun Activity.browser(url: String, @StringRes titleId: Int = 0) {
    dialog(DialogParam().apply {
        resid = R.layout.dlg_web
        if (titleId != 0) {
            title = string(titleId)
        }
    }).show().run {
        web.run {
            loadUrl(url)
            web.layoutParams = FrameLayout.LayoutParams(BkApp.screenX(), BkApp.screenY())
        }
        fullscreen()
    }
}

inline fun Dialog.fullscreen() {
    window.run {
        val attr = attributes
        attr.run {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        attributes = attr

        setBackgroundDrawableResource(android.R.color.transparent)
    }
}

//inline fun ProgressDialog.progressFileSize(unit: Int, value: Long) {
//    setProgress((value shr (10 * unit)).toInt())
//    setProgressNumberFormat(value.toFileSizeString(unit))
//}

class ProgressNumberFormat(val progress: ProgressDialog, val maxValue: Long) {
    var unitCount: Int = 0
    var unitChar: Char
    var totalSize: String

    init {
        calUnitCount()

        var count = unitCount
        if (maxValue > 1024) {
            ++count
        }

        progress.max = calSize(maxValue).toInt()

        unitChar  = " KMGTPE"[count]
        totalSize = String.format("%.1f%cB", progress.max.toFloat() / 1024f, unitChar)

        formatString(0)
    }

    private fun calUnitCount() {
        var size = maxValue

        while (size > 1024 * 1024) {
            unitCount++
            size = size shr 10
        }
    }

    private fun calSize(value: Long): Long {
        return (value shr (10 * unitCount))
    }

    fun formatString(current: Long) {
        val value  = calSize(current).toInt()
        val format = String.format("%.1f%cB/%s", value.toFloat() / 1024f, unitChar, totalSize)

        progress.progress = value
        progress.setProgressNumberFormat(format)
    }
}

interface IDialog {
    fun show(activity: Activity, message: String, title: String? = null)
    fun show(activity: Activity, @StringRes messageId: Int, @StringRes titleId: Int = 0)
}

//class BkDialog : IDialog {
//    override fun show(activity: Activity, @StringRes messageId: Int, @StringRes titleId: Int) {
//        activity.dialog(DialogParam().apply {
//            if (messageId != 0) {
//                this.message = activity.string(messageId)
//            }
//
//            if (titleId != 0) {
//                this.title = activity.string(titleId)
//            }
//        })
//    }
//
//    override fun show(activity: Activity, message: String, title: String?) {
//        activity.dialog(DialogParam().apply {
//            this.title   = title
//            this.message = message
//        })
//    }
//}
