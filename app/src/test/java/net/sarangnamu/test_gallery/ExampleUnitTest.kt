package net.sarangnamu.test_gallery

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

//    @Test
//    fun test() {
//        var test = "1219950180111108236115111016623101401611235115012312161151110101111127"
//        test = test.reversed()
//
//        var i = 0
//        var result: String = ""
//        while (i < test.length) {
//            var str = test.substring(i, i + 2)
//            var convert = str.toInt()
//
//            if (convert == 32) {
//                result += " "
//
//                i += 2
//            } else if (convert >= 65  && convert <= 99) {
//                result += convert.toChar().toString()
//
//                i += 2
//            } else {
//                str = test.substring(i, i + 3)
//                convert = str.toInt()
//                result += convert.toChar().toString()
//
//                i += 3
//            }
//        }
//    }
//
//    fun swap(b : String, resultList: ArrayList<String>){
//        var i = 0
//        var j = 0
//
//        while (i < b.length) {
//            j = 0
//            while (j < b.length) {
//                if (i != j) {
//                    val data = b.toMutableList()
//
//                    val temp = data[i]
//                    data[i] = data[j]
//                    data[j] = temp
//
//                    val convert = data.joinToString("")
//                    if (!resultList.contains(convert)) {
//                        resultList.add(convert)
//                    }
//                }
//
//                ++j
//            }
//
//            ++i
//        }
//    }
//
//    fun shift(i: Int, data: String): String {
//        val num = data.substring(i , i + 1)
//        var newdata = data.removeRange(i, i + 1)
//        newdata += num
//
//        return newdata
//    }
//
//    @Test
//    fun test5() {
//        // 문제 이해하기가 힘들 세 -_ -;;;
//        val a = "1234"
//        var b = "1213"
//        var i = 0
//        val resultList = arrayListOf<String>()
//
//        while (i < a.length) {
//            swap(b, resultList)
//            b = shift(0, b)
//
//            if (!resultList.contains(b)) {
//                resultList.add(b)
//            }
//
//            ++i
//        }
//
//        resultList.sort()
//    }
}
