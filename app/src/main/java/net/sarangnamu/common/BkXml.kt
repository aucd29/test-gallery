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

import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 *
 *  class TestParser : XPathBase() {
        override fun parsing() {
            val count  = int("count(//xsync)")
            val number = string("//rgist/text()")

            if (TextUtils.isEmpty(number)) {
                val msg = string("//message/text()")
                val err = string("//error_code/text()")
            } else {
                var i=0
                while (i++ <count) {
                    // TODO
                }
            }
        }
    }
 */
abstract class XPathBase {
    companion object {
        private val log = LoggerFactory.getLogger(XPathBase::class.java)
    }

    val xpath   = XPathFactory.newInstance().newXPath()
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    lateinit var document: Document

    /**
     * xml 파일 핸을 연다
     */
    @Throws(Exception::class)
    fun loadXml(fp: File) {
        if (!fp.exists()) {
            throw FileNotFoundException(fp.absolutePath)
        }

        document = builder.parse(fp)
        parsing()
    }

    /**
     * xml input stream 을 연다
     */
    @Throws(Exception::class)
    fun loadXml(ism: InputStream) {
        document = builder.parse(ism)
        parsing()
    }

    /**
     * xml string 로드 한다
     */
    @Throws(Exception::class)
    fun loadXml(xml: String) {
        document = builder.parse(InputSource(StringReader(xml)))
        parsing()
    }

    /** string 으로 반환 */
    fun string(expr: String) = xpath.evaluate(expr, document, XPathConstants.STRING).toString().trim()
    /** int 로 반환 */
    fun int(expr: String)    = string(expr).toInt()
    /** float 으로 반환 */
    fun float(expr: String)  = string(expr).toFloat()
    /** double 로 반환 */
    fun double(expr: String) = string(expr).toDouble()
    /** bool 로 반환 */
    fun bool(expr: String)   = string(expr).toBoolean()

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // ABSTRACT
    //
    ////////////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    abstract fun parsing()
}
