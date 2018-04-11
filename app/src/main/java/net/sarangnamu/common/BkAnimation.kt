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

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.ColorRes
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun Animator.addEndListener(crossinline f: (Animator?) -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {}
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) = f(animation)
    })
}

inline fun View.fadeColor(fcolor: Int, scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    ObjectAnimator.ofObject(this, "backgroundColor", ArgbEvaluator(), fcolor, scolor).apply {
        this.duration = duration
        f?.let { this.addEndListener(it) }
    }.start()
}

inline fun View.fadeColorRes(@ColorRes fcolor: Int, @ColorRes scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    fadeColor(context.color(fcolor), context.color(scolor), f, duration)
}

inline fun Window.fadeStatusBar(fcolor:Int, scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val from = context.color(fcolor)
        val to   = context.color(scolor)

        ValueAnimator.ofArgb(from, to).apply {
            this.duration = duration
            this.addUpdateListener { statusBarColor = it.animatedValue as Int }
            f?.let { this.addEndListener(it) }
        }.start()
    }
}

inline fun Window.fadeStatusBarRes(@ColorRes fcolor: Int, @ColorRes scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    fadeStatusBar(context.color(fcolor), context.color(scolor), f, duration)
}