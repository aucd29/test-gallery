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

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.io.File

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

////////////////////////////////////////////////////////////////////////////////////
//
//
//
////////////////////////////////////////////////////////////////////////////////////

/** try catch block */
inline fun <T, R> T.trycatch(block: (T) -> R) : R {
    try {
        return block(this)
    } catch (e: Exception) {
        Log.e("trycatch", "ERROR: " + e.message)
        throw e
    }
}

internal class Async(val background: (() -> Boolean)?, val post: ((result: Boolean) -> Unit)?)
    : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        return background?.invoke() ?: true
    }

    override fun onPostExecute(result: Boolean) {
        post?.invoke(result)
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// SYSTEM
//
////////////////////////////////////////////////////////////////////////////////////

class BkSystem {
    companion object {
        @Suppress("DEPRECATION")
        fun blockSize(path: File): Long {
            val blockSize: Long
            val availableBlocks: Long
            val stat = StatFs(path.path)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                blockSize       = stat.blockSizeLong
                availableBlocks = stat.availableBlocksLong
            } else {
                blockSize       = stat.blockSize.toLong()
                availableBlocks = stat.availableBlocks.toLong()
            }

            return availableBlocks * blockSize
        }
    }
}