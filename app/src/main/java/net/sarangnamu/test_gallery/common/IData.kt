package net.sarangnamu.test_gallery.common

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 13.. <p/>
 */

interface IData<T> {
    @Throws(Exception::class)
    fun init(html: String)
    fun next(): Boolean
    fun limit(limit: Int)
    fun list(): ArrayList<T>
    fun hasNext(): Boolean
}
