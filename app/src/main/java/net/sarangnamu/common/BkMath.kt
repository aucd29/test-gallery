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

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

const val UNIT_STRING: String = " KMGTPE"

//inline fun Long.unitCount(): Int {
//    var u = 0
//    var size = this
//
//    while (size > 1024 * 1024) {
//        u++
//        size = size shr 10
//    }
//
//    if (size > 1024) {
//        u++
//    }
//
//    return u
//}
//
//inline fun Long.shr10(unit: Int): Int {
//    return (this shr (10 * unit)).toInt()
//}
//
//inline fun Long.toFileSizeString(unit: Int): String {
//    val size = this shr (10 * unit)
//
//    return String.format("%.1f %cB", size / 1024f, " KMGTPE"[unit])
//}

inline fun Long.toFileSizeString(): String {
    var u = 0
    var size = this

    while (size > 1024 * 1024) {
        u++
        size = size shr 10
    }

    if (size > 1024) {
        u++
    }

    return String.format("%.1f %cB", size / 1024f, UNIT_STRING[u])
}

inline fun Double.toFileSizeString(unit: Int): String {
    val size = this.toLong() shr (10 * unit)

    return String.format("%.1f %cB", size / 1024f, UNIT_STRING[unit])
}

inline fun Double.toFileSizeString(): String {
    var u = 0
    var size = this.toLong()

    while (size > 1024 * 1024) {
        u++
        size = size shr 10
    }

    if (size > 1024) {
        u++
    }

    return String.format("%.1f %cB", size / 1024f, UNIT_STRING[u])
}