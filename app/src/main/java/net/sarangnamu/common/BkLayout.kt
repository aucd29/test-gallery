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
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun View.string(@StringRes resid: Int) = context.resources.getString(resid)

// https://antonioleiva.com/functional-operations-viewgroup-kotlin/
inline val ViewGroup.views: List<View>
    get() = (0..childCount - 1).map { getChildAt(it) }

abstract class FrameBase : FrameLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}

abstract class LinearBase : LinearLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}

abstract class RelativeBase : RelativeLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}