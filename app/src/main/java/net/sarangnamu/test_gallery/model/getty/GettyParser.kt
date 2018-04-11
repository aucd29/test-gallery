package net.sarangnamu.test_gallery.model.getty

import net.sarangnamu.common.XPathBase
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 *
 * https://www.freeformatter.com/xpath-tester.html

<!-- REPEATER -->
<div class="gallery-item-group lastitemrepeater">
    <a href="/Picture-Library/Image.aspx?id=7824"><img src="/Images/Thumbnails/1552/155296.jpg" class="picture"/></a>

    <div class="gallery-item-caption">
    <p><a href="/Picture-Library/Image.aspx?id=7824">We Know Our Place</a></p>
    </div>
</div>

<div class="gallery-item-group exitemrepeater">
    <a href="/Picture-Library/Image.aspx?id=2455"><img src="/Images/Thumbnails/1343/134346.jpg" class="picture"/></a>

    <div class="gallery-item-caption">
    <p><a href="/Picture-Library/Image.aspx?id=2455">Aerial Miami Beach</a></p>
    </div>
</div>
<!-- REPEATER ENDS -->
 */

class GettyParser : XPathBase() {
    companion object {
        private val log = LoggerFactory.getLogger(GettyParser::class.java)
    }

    val imageList = arrayListOf<GettyImageInfo>()

    override fun parsing() {
        val countExpr   = "count(//div[contains(@class, 'gallery-item-group')])"
        val imgPathExpr = "//div[contains(@class, 'gallery-item-group')][%d]/a/img/@src"
        val captionExpr = "//div[contains(@class, 'gallery-item-group')][%d]/div/p/a/text()"
        val count = int(countExpr)

        if (log.isDebugEnabled) {
            log.debug("IMAGE COUNT : $count")
        }

        (1..count).forEach {
            val imgPath = string(imgPathExpr.format(it))
            val caption = string(captionExpr.format(it))

//            if (log.isDebugEnabled) {
//                log.debug("src = $imgPath, caption = $caption")
//            }

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
}