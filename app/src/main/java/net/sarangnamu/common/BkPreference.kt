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
import android.content.Context
import android.util.Base64

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

/**
 * preference 설정
 */
@SuppressLint("CommitPrefEdits")
inline fun Context.config(param: Preference): String? {
    val pref = getSharedPreferences("shared.pref", Context.MODE_PRIVATE)

    if (param.write) {
        // write mode
        val editor = pref.edit().putString(param.key, param.value)
        if (param.async) {
            editor.apply()
        } else {
            editor.commit()
        }
    } else {
        // read mode
        val value = pref.getString(param.key, param.value)
        return value?.decodeBase64()
    }

    return null
}

inline fun Context.pref(param: Preference): String? {
    return config(param)
}

class Preference {
    lateinit var key: String
    var value: String? = null
    var write = false
    var async = false

    /**
     * read shared preference
     */
    fun read(key: String, value: String?) {
        data(key, value)
        write = false
    }

    /**
     * write shared preference
     */
    fun write(key: String, value: String?) {
        data(key, value)
        write = true
    }

    private fun data(key: String, value: String?) {
        this.key   = key
        this.value = value?.encodeBase64()
    }
}