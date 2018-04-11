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
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

inline fun Float.pixelToDp()    = this / BkApp.context().displayDensity()
inline fun Float.dpToPixel()    = this * BkApp.context().displayDensity()
inline fun Float.doToPixelInt() = (this * BkApp.context().displayDensity()).toInt()

inline fun Int.pixelToDp()      = this / BkApp.context().displayDensity()
inline fun Int.dpToPixel()      = this * BkApp.context().displayDensity()
inline fun Int.doToPixelFloat() = (this * BkApp.context().displayDensity())

class BkDim {
    companion object {
        fun dpToPixel(dp1: Float, dp2: Float, dp3: Float, dp4: Float) =
            listOf(dp1.dpToPixel(), dp2.dpToPixel(), dp3.dpToPixel(), dp4.dpToPixel())

        fun dpToPixel(dpList: List<Float>) =
            listOf(dpList.get(0).dpToPixel(), dpList.get(1).dpToPixel(),
                   dpList.get(2).dpToPixel(), dpList.get(3).dpToPixel())

        fun dpToPixelInt(dp1: Float, dp2: Float, dp3: Float, dp4: Float) =
            listOf(dp1.doToPixelInt(), dp2.doToPixelInt(), dp3.doToPixelInt(), dp4.doToPixelInt())

        fun dpToPixelInt(dpList: List<Float>) =
            listOf(dpList.get(0).doToPixelInt(), dpList.get(1).doToPixelInt(),
                   dpList.get(2).doToPixelInt(), dpList.get(3).doToPixelInt())
    }
}