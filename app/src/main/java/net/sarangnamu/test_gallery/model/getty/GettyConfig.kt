package net.sarangnamu.test_gallery.model.getty

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
 * 헐 -_- API 문서가 안열린다. ㅋㅋㅋ
 */
class GettyConfig {
    companion object {
        val api_key             = "pvb39rtvpuwhbdgm5s6mfz58"
        val api_secret          = "HFpbm3f8pM63pcaX8fGxZuEnN7mpHXMPgasRTF47N9kjZ"

        val AUTH                = "https://api.gettyimages.com/oauth2/token"
        val GRANT_TYPE          = "grant_type"
        val CLIENT_CREDENTIALS  = "client_credentials"
        val CLIENT_ID           = "client_id"
        val CLIENT_SECRET       = "client_secret"


        val URL = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx"
    }
}

data class GettyImageInfo(val path: String, val caption: String)