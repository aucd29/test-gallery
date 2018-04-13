package net.sarangnamu.test_gallery

import android.os.Build
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import net.sarangnamu.common.BkApp
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 4. 11.. <p/>
 *
    아래 링크의 이미지들을 http 로 받아와 list 또는 grid 형태로 출력하는 간단한 앱을 제작하여 제출
    http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx

    [제약 사항]
    1. 이미지 로더를 제외한 오픈소스 라이브러리 사용은 가능하나 동작 원리 설명이 가능해야 함
    2. 이미지 로더(Glide, Fresco, Picasso, UniversalImageLoader 등) 라이브러리 사용은 불가, 관련 기능을 직접 구현해야 함
    3. 메모리 캐시와 디스크 캐시를 구현하여 이미지를 중복 다운로드 받지 않도록 함
    4. 화면 회전이 가능해야 하며, 화면 회전 시 페이지 http 중복 요청하지 않도록 함
 */
class MainApp : BkApp() {
    override fun onCreate() {
        super.onCreate()

        val sb = StringBuffer()
        sb.append("\n")
        sb.append("=====================\n")
        sb.append(getString(R.string.app_name)).append("\n")
        sb.append("=====================\n")
        sb.append("MODEL        : ").append(Build.MODEL).append("\n")
        sb.append("SDK          : ").append(Build.VERSION.SDK_INT).append("\n")
        sb.append("VERSION CODE : ").append(BuildConfig.VERSION_CODE).append("\n")
        sb.append("VERSION NAME : ").append(BuildConfig.VERSION_NAME).append("\n")
        sb.append("=====================")
        Log.i("[BKLOG]", sb.toString())

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your get in this process.
            return
        }

        LeakCanary.install(this)
    }
}