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
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Environment
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

/**
 * pkgName 에 해당하는 앱이 foreground 인지 확인
 */
inline fun Context.isForegroundApp(pkgName: String): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses.filter {
        it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && it.processName == pkgName }.size == 1
}

/**
 * 현재 앱이 foreground 인지 확인
 */
inline fun Context.isForegroundApp(): Boolean {
    return isForegroundApp(packageName)
}

/**
 * sdcard 내 app 경로 전달
 */
inline fun Context.externalFilePath() = getExternalFilesDir(null).absolutePath

/**
 * display density 반환
 */
inline fun Context.displayDensity() = resources.displayMetrics.density

/**
 * open keyboard
 */
inline fun Context.showKeyboard(view: View?) {
    view?.let {
        it.postDelayed({
            it.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, InputMethodManager.SHOW_FORCED)
        }, 400)
    }
}

/**
 * hide keyboard
 */
inline fun Context.hideKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * force hide keyboard
 */
inline fun Context.forceHideKeyboard(window: Window?) {
    window?.run { setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) }
}

inline fun Context.string(@StringRes resid: Int): String? = getString(resid)
inline fun Context.drawable(@DrawableRes resid: Int): Drawable? = ContextCompat.getDrawable(this, resid)

////////////////////////////////////////////////////////////////////////////////////
//
// ENVIRONMENT EXTENSION
//
////////////////////////////////////////////////////////////////////////////////////

/** sdcard 가 존재하는지 확인 */
inline fun Context.hasSd() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/** data directory 에 사이즈 반환 */
inline fun Context.dataDirSize() =
        BkSystem.blockSize(Environment.getDataDirectory()).toFileSizeString()

/** sdcard 사이즈 반환 */
inline fun Context.sdSize() =
        BkSystem.blockSize(Environment.getExternalStorageDirectory()).toFileSizeString()

/** sdcard 경로 반환 */
inline fun Context.sdPath() =
        Environment.getExternalStorageDirectory().getAbsolutePath()

fun Context.async(background: (() -> Boolean)? = null, post: ((result: Boolean) -> Unit)? = null) {
    Async(background, post).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}

fun Context.clipboard(key: String): String? {
    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return manager.primaryClip?.getItemAt(0)?.text.toString()
}

fun Context.clipboard(key: String, value: String?) {
    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    manager.primaryClip = ClipData.newPlainText(key, value)
}