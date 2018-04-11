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

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 */

inline fun Context.color(name: String): ColorDrawable {
    val id = resources.getIdentifier(name, "color", packageName)
    return ColorDrawable(id)
}

inline fun Context.color(@ColorRes resid: Int): Int = ContextCompat.getColor(this, resid)
inline fun Context.colorList(@ColorRes resid: Int): ColorStateList? = ContextCompat.getColorStateList(this, resid)

class ColorSelector(context: Context): SelectorBase(context) {
    override fun drawable(name: String): Drawable {
        return context.color(name)
    }
}
