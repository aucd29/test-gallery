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

package net.sarangnamu.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

//https://github.com/mohamad-amin/Android-FastSearch/blob/master/app/src/main/java/com/mohamadamin/fastsearch/free/utils/BitmapUtils.java

fun Drawable.bitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val bmp = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
    val canvas = Canvas(bmp)

    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bmp
}

fun Bitmap.resize(w: Int, h: Int): Bitmap {
    val matrix = Matrix()
    val scaleW = w.toFloat() / width.toFloat()
    val scaleH = h.toFloat() / height.toFloat()

    matrix.postScale(scaleW, scaleH)

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.ratioResize(@DimenRes resid: Int): Bitmap {
    val context = BkApp.context()
    val size = context.resources.getDimensionPixelSize(resid)

    if (width > height) {
        return if (width > size) {
            val scale = size / width
            resize(width * scale, height * scale)
        } else this
    } else if (height > width) {
        return if (height > size) {
            val scale = size / height
            resize(width * scale, height * scale)
        } else this
    } else {
        return if (width > size) resize(size, size) else this
    }
}