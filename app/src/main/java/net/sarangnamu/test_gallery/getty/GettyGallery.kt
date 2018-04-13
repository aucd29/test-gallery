package net.sarangnamu.test_gallery.getty

import net.sarangnamu.common.XPathBase
import net.sarangnamu.test_gallery.common.DataManager
import net.sarangnamu.test_gallery.common.IData
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 * 헐 -_- API 문서가 안열린다.
 */

////////////////////////////////////////////////////////////////////////////////////
//
// GettyConfig
//
////////////////////////////////////////////////////////////////////////////////////

class GettyConfig {
    companion object {
        // 멋 모르고 oauth 로 하다가 문제 다시 보고 http 로 변경

//        val api_key             = "pvb39rtvpuwhbdgm5s6mfz58"
//        val api_secret          = "HFpbm3f8pM63pcaX8fGxZuEnN7mpHXMPgasRTF47N9kjZ"
//
//        val AUTH                = "https://api.gettyimages.com/oauth2/token"
//        val GRANT_TYPE          = "grant_type"
//        val CLIENT_CREDENTIALS  = "client_credentials"
//        val CLIENT_ID           = "client_id"
//        val CLIENT_SECRET       = "client_secret"

        const val BASE_URL = "http://www.gettyimagesgallery.com"
        const val LIST_URL = "${BASE_URL}/collections/archive/slim-aarons.aspx"
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// GettyImageInfo
//
////////////////////////////////////////////////////////////////////////////////////

data class GettyImageInfo(val path: String, val caption: String)

////////////////////////////////////////////////////////////////////////////////////
//
// GettyParser
//
////////////////////////////////////////////////////////////////////////////////////

class GettyParser : XPathBase(), IData<GettyImageInfo> {
    companion object {
        private val log = LoggerFactory.getLogger(GettyParser::class.java)
    }

    val imageList = arrayListOf<GettyImageInfo>()
    var total = 0
    var limit = 40
    var first = 1

    override fun parsing() {
        val countExpr = "count(//div[contains(@class, 'gallery-item-group')])"

        total = int(countExpr)

        if (log.isDebugEnabled) {
            log.debug("IMAGE COUNT : $total")
        }
    }

    @Throws(Exception::class)
    override fun init(html: String) {
        this.first = 1

        val fpos = html.indexOf("<!-- REPEATER -->")
        val lpos = html.lastIndexOf("<!-- REPEATER ENDS -->")

        if (fpos == -1 || lpos == -1) {
            throw Exception("ERROR: fpos = $fpos, lpos = $lpos")
        }

        val rawHtml = html.substring(fpos, lpos)

        loadXml("<root>${rawHtml}</root>")
    }

    /**
     * 다음 데이터 가져오기
     */
    override fun next(): Boolean {
        val first = this.first + this.limit
        val last  = first + this.limit

        select(first, last)

        return last < total
    }

    /**
     * 한번에 불러드릴 데이터 양 설정
     */
    override fun limit(limit: Int) {
        this.limit = limit
    }

    /**
     * dom + xpath 형태로 원하는 데이터를 call
     */
    private fun select(startIndex: Int, endIndex: Int) {
        if (log.isDebugEnabled) {
            log.debug("SELECT ($startIndex, $endIndex)")
        }

        val imgPathExpr = "//div[contains(@class, 'gallery-item-group')][%d]/a/img/@src"
        val captionExpr = "//div[contains(@class, 'gallery-item-group')][%d]/div/p/a/text()"

        var last  = endIndex
        var first = startIndex

        if (first < 1) {
            first = 1
        }

        if (last > total) {
            last = total
        }

        (first..last).forEach {
            val imgPath = string(imgPathExpr.format(it))
            val caption = string(captionExpr.format(it))

            if (log.isTraceEnabled) {
                log.trace("src = $imgPath, caption = $caption")
            }

            imageList.add(GettyImageInfo(imgPath, caption))
        }

        // 엄청 느린데?  ㄷ ㄷ SAX 로 해야 되나?
        if (log.isDebugEnabled) {
            log.debug("DONE")
        }
    }

    override fun list(): ArrayList<GettyImageInfo> = imageList
}