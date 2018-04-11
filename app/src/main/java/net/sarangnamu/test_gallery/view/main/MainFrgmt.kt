package net.sarangnamu.test_gallery.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.sarangnamu.common.FrgmtBase
import net.sarangnamu.test_gallery.R

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 */

class MainFrgmt : FrgmtBase() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initLayout() {
    }

    override fun layoutId(): Int {
        return R.layout.main_layout
    }
}