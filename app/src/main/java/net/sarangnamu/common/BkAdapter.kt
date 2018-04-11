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
import android.support.annotation.LayoutRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.reflect.ParameterizedType

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 *
 * ```kotlin
 * **example**
 *
 *  val dataList = arrayListOf("test1", "test2", "test3", "test4")
    adapter = object: V7Adapter<String, ViewHolder>(this, R.layout.main_item, dataList) {
        override fun bindData(holder: ViewHolder, data: String) {
            holder.a.text = data
        }
    }

    adapter.clickListener = { v, p ->
        Log.e("TAG", "POSITION: $p")
    }

    recyclerView.verticalLayout()
    recyclerView.adapter = adapter
 * ```
 * ```kotlin
 * **update data**
 * val dataList = arrayListOf("test1", "test2", "test3", "test4")
 * adapter.invalidate(dataList)
 * ```
 * ```kotlin
 * **update data**
 * adapter.invalidate(1, "1번째 데이터 갱신")
 * ```
 */

abstract class V7Adapter<T, H: RecyclerView.ViewHolder>(
        open var context: Context,
        @LayoutRes open var id: Int,
        open var dataList: ArrayList<T>) : RecyclerView.Adapter<H>() {

    var clickListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val klass       = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<H>
        val constructor = klass.getDeclaredConstructor(*arrayOf<Class<*>>(View::class.java))
        val view        = LayoutInflater.from(context).inflate(id, parent,false)
        val viewHolder  = constructor.newInstance(*arrayOf(view))

        clickListener?.let { view.setOnClickListener { v -> it(v, viewHolder.layoutPosition) } }

        return viewHolder
    }

    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: H, position: Int) = bindData(holder, dataList.get(position))

    fun invalidate(dataList: ArrayList<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun invalidate(pos: Int, data: T) {
        this.dataList.set(pos, data)
        notifyItemChanged(pos)
    }

    abstract fun bindData(holder: H, data: T)
}

inline fun RecyclerView.verticalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
}

inline fun RecyclerView.horizontalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
}

inline fun RecyclerView.gridLayout(col: Int) {
    layoutManager = GridLayoutManager(context, col)
}
