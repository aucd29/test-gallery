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

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.io.File

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

/**
 * 문자열 끝에 / 이 존재하는지 확인
 */
inline fun CharSequence.isLastSlash(): Boolean =
        !isEmpty() && lastIndexOf(File.separator) != -1

/**
 * 문자열 끝에 / 를 붙임
 */
inline fun String.addLastSlash(): String {
    if (isEmpty()) {
        return this
    }

    if (!endsWith(File.separator)) {
        return this + File.separator
    }

    return this
}

/**
 * 파일명.확장자 반환
 */
inline fun String.fileFullName(): String = substringAfterLast('/')

/**
 * 파일 경로만 반환
 */
inline fun String.filePath(): String = substringBeforeLast('/', "")

/**
 * 파일이름만 반환
 */
inline fun String.fileName(): String = substringAfterLast('/')

/**
 * 파일 확장자만 반환
 */
inline fun String.fileExtension(): String = fileFullName().substringBeforeLast('.')

inline fun String.encodeBase64(): String = Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
inline fun String.decodeBase64(): String = String(Base64.decode(this, Base64.DEFAULT))

inline fun String.html(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}