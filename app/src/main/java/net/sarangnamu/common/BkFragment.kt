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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.sarangnamu.test_gallery.R
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline val FragmentManager.frgmts: List<Fragment>
    get() = (0..backStackEntryCount - 1).map { findFragmentByTag(getBackStackEntryAt(it).name) }

inline val FragmentManager.current: Fragment
    get() = frgmts.last()

inline val FragmentManager.count: Int
    get() = backStackEntryCount

inline fun FragmentManager.add(id: Int, clazz: Class<out Any>, noinline listener: ((FragmentManager, FragmentTransaction) -> Unit)? = null) {
    val frgmt = clazz.newInstance() as Fragment
    val transaction = beginTransaction()

    if (frgmt.isVisible) {
        return
    }

    listener?.invoke(this, transaction)

    transaction.add(id, frgmt, frgmt.javaClass.name)
    transaction.commit()
}

inline fun FragmentManager.replace(id: Int, clazz: Class<out Any>, noinline listener: ((FragmentManager, FragmentTransaction) -> Unit)? = null,
                            bundle: Bundle? = null, stack: Boolean = true): Fragment {
    val existFrgmt = findFragmentByTag(clazz.name)
    if (existFrgmt != null && existFrgmt.isVisible) {
        // manager 내에 해당 fragment 가 이미 존재하면 해당 fragment 를 반환 한다
        return existFrgmt
    }

    val frgmt = clazz.newInstance() as Fragment
    val transaction = beginTransaction()

    bundle?.let { frgmt.arguments = it }
    listener?.invoke(this, transaction)

    transaction.replace(id, frgmt, frgmt.javaClass.name)
    if (stack) {
        transaction.addToBackStack(frgmt.javaClass.name)
    }

    transaction.commit()

    return frgmt
}

inline fun FragmentManager.pop() {
    popBackStack()
}

inline fun FragmentManager.popAll() {
    (0..count - 1).map { popBackStack(it, FragmentManager.POP_BACK_STACK_INCLUSIVE) }
}

inline val FragmentTransaction.ANI_HORIZONTAL: Int
    get() = 1
inline val FragmentTransaction.ANI_VERTICAL: Int
    get() = 2

inline fun FragmentTransaction.animate(anim: Int) {
    when (anim) {
        ANI_HORIZONTAL -> setCustomAnimations(R.anim.slide_in_current, R.anim.slide_in_next,
                R.anim.slide_out_current, R.anim.slide_out_prev)
        ANI_VERTICAL   -> setCustomAnimations(R.anim.slide_up_current, R.anim.slide_up_next,
                R.anim.slide_down_current, R.anim.slide_down_prev)
        else -> setCustomAnimations(0, 0, 0, 0)
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// Fragment
//
////////////////////////////////////////////////////////////////////////////////////

abstract class FrgmtBase : Fragment() {
    protected lateinit var base: ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val id = layoutId()
        if (id == 0) {
            base = defaultView()
        } else {
            base = inflater.inflate(id, container!!) as ViewGroup
        }

        base.isClickable = true
        base.setBackgroundColor(0xffffff)

        initLayout()

        return base
    }

    protected fun defaultView() : ViewGroup {
        val view = LinearLayout(activity)
        view.lpmm()
        view.gravity = Gravity.CENTER

        val text = TextView(activity)
        text.gravityCenter()
        text.text = javaClass.simpleName

        view.addView(text)

        return view
    }

    open fun defaultMessage() : String {
        return javaClass.simpleName
    }

    abstract fun layoutId(): Int
    abstract fun initLayout()
}

abstract class RuleFrgmtBase: FrgmtBase() {
    private val log = LoggerFactory.getLogger(RuleFrgmtBase::class.java)

    companion object {
        protected val PREFIX = "view_"
        protected val SUFFIX = "Frgmt"
        protected val LAYOUT = "layout"
    }

    lateinit var className: String

    override fun defaultMessage(): String {
        return super.defaultMessage() + "\nFILE NOT FOUND (${inflateName()}.xml)"
    }

    override fun layoutId(): Int {
        val name = prefix() + inflateName()

        if (log.isDebugEnabled) {
            log.debug("INFLATE NAME : $name")
        }

        return resources.getIdentifier(name, LAYOUT, activity?.packageName)
    }

    fun inflateName(): String {
        if (!TextUtils.isEmpty(className)) {
            return className
        }

        val name = javaClass.simpleName.replace(suffix(), "")
        className = name.get(0).toLowerCase().toString()

        name.substring(1, name.length).forEach {
            if (it.isUpperCase()) {
                className += "_" + it.toLowerCase()
            } else {
                className += it
            }
        }

        return className
    }

    open fun prefix(): String {
        return PREFIX
    }

    open fun suffix(): String {
        return SUFFIX
    }
}