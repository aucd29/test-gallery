package net.sarangnamu.test_gallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initFragment()
        }
    }

    fun initFragment() {
    }
}
