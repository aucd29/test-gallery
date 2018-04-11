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

import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 28.. <p/>
 */

// LinearLayout

inline fun LinearLayout.lp(w: Int, h: Int) {
    layoutParams = LinearLayout.LayoutParams(w, h)
}

inline fun LinearLayout.lpmm() {
    layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
}

inline fun LinearLayout.lpwm() {
    layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
}

inline fun LinearLayout.lpmw() {
    layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
}

inline fun LinearLayout.lpww() {
    layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
}

// RelativeLayout

inline fun RelativeLayout.lp(w: Int, h: Int) {
    layoutParams = RelativeLayout.LayoutParams(w, h)
}

inline fun RelativeLayout.lpmm() {
    layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
}

inline fun RelativeLayout.lpwm() {
    layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
}

inline fun RelativeLayout.lpmw() {
    layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
}

inline fun RelativeLayout.lpww() {
    layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
}

// FrameLayout

inline fun FrameLayout.lp(w: Int, h: Int) {
    layoutParams = FrameLayout.LayoutParams(w, h)
}

inline fun FrameLayout.lpmm() {
    layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
}

inline fun FrameLayout.lpwm() {
    layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT)
}

inline fun FrameLayout.lpmw() {
    layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
}

inline fun FrameLayout.lpww() {
    layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
}

// ViewGroup

inline fun ViewGroup.lp(w: Int, h: Int) {
    layoutParams = ViewGroup.LayoutParams(w, h)
}

inline fun ViewGroup.lpmm() {
    layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
}

inline fun ViewGroup.lpwm() {
    layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
}

inline fun ViewGroup.lpmw() {
    layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}

inline fun ViewGroup.lpww() {
    layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}

// Window

inline fun Window.lp(w: Int, h: Int) {
    val lp = attributes
    lp.width = w
    lp.height = h

    attributes = lp
}

inline fun Window.lpmm() {
    attributes = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
}

inline fun Window.lpwm() {
    attributes = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT)
}

inline fun Window.lpmw() {
    attributes = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
}

inline fun Window.lpww() {
    attributes = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
}