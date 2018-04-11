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

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 12. 21.. <p/>
 */

class MenuManager {
    private object Holder { val INSTANCE = MenuManager() }

    companion object {
        val get: MenuManager by lazy { Holder.INSTANCE }
    }

    private lateinit var popup: PopupMenu

    fun show(context: Context, v: View, resid: Int, listener: (MenuItem?) -> Boolean) {
        popup = PopupMenu(context, v)
        popup.menuInflater.inflate(resid, popup.menu)
        popup.setOnMenuItemClickListener(listener)
        popup.show()
    }
}