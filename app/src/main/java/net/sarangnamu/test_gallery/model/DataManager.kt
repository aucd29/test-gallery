package net.sarangnamu.test_gallery.model

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 * API PAGE = http://developers.gettyimages.com/
 * REGISTER = https://developer.gettyimages.com/member/register
 */

class DataManager private constructor() {
    private object Holder { val INSTANCE = DataManager() }

    companion object {
        val instance: DataManager by lazy { Holder.INSTANCE }
    }

    init {

    }

    fun load() {

    }

    fun get() {

    }

    fun free() {

    }
}