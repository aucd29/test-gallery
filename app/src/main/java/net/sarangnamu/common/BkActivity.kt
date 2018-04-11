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
import android.os.AsyncTask
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import net.sarangnamu.test_gallery.R

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 29.. <p/>
 */

// https://blog.asamaru.net/2015/12/15/android-app-finish/
inline fun Activity.processKill() {
    moveTaskToBack(true);
    ActivityCompat.finishAffinity(this)
    android.os.Process.killProcess(android.os.Process.myPid())
}

/**
 * app 강제 종료
 */
inline fun Activity.kill() {
    try {
        System.runFinalizersOnExit(true)
    } catch (ignored: Exception) {
    } finally {
        System.exit(0)
    }
}

inline fun Activity.toast(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
inline fun Activity.toastLong(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
inline fun Activity.snackbar(view: View, msg: String): Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
inline fun Activity.snackbarLong(view: View, msg: String): Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)

inline fun Activity.toast(@StringRes resid: Int): Toast = toast(getString(resid))
inline fun Activity.toastLong(@StringRes resid: Int): Toast = toastLong(getString(resid))
inline fun Activity.snackbar(view: View, @StringRes resid: Int): Snackbar = snackbar(view, getString(resid))
inline fun Activity.snackbarLong(view: View, @StringRes resid: Int): Snackbar = snackbarLong(view, getString(resid))
inline fun Activity.checkBackPressed(view: View?) = AppCloser(this, view)

open class AppCloser(var activity: Activity, var view: View? = null) {
    companion object {
        val delay = 2000
    }

    var pressedTime: Long = 0
    var toast: Toast? = null
    var snackbar: Snackbar? = null

    inline fun time() = pressedTime + delay

    fun onBackPressed() {
        if (System.currentTimeMillis() > time()) {
            pressedTime = System.currentTimeMillis()
            show()

            return
        }

        if (System.currentTimeMillis() <= time()) {
            view?.let { snackbar?.dismiss() } ?: toast?.cancel()
            ActivityCompat.finishAffinity(activity)
        }
    }

    fun show() {
        if (view != null) {
            snackbar = activity.snackbar(view!!, R.string.activity_click_exit_again)
            snackbar?.show()
        } else {
            toast = activity.toast(R.string.activity_click_exit_again)
            toast?.show()
        }
    }
}

fun Activity.async(background: (() -> Boolean)? = null, post: ((result: Boolean) -> Unit)? = null) {
    Async(background, post).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}