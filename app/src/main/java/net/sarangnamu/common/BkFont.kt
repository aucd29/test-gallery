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
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun TextView.roboto() {
    font("Roboto-Light")
}

inline fun TextView.font(name: String) {
    typeface = Typeface.createFromAsset(context.assets, "fonts/$name.ttf")
}

fun ViewGroup.font(name: String) {
    views.forEach {
        when (it) {
            is TextView -> it.font(name)
            is ViewGroup -> it.font(name)
        }
    }
}

//fun RadioGroup.font(name: String) {
//    views.forEach {
//        when (it) {
//            is TextView -> it.font(name)
//            is ViewGroup -> it.font(name)
//        }
//    }
//}

class RobotoLightTextView: TextView {
    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initLayout()
    }

    protected fun initLayout() {
        roboto()
    }
}