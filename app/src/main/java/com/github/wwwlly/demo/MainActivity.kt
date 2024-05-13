package com.github.wwwlly.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.wwwlly.activity.ARouterRootFragmentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            ARouterRootFragmentActivity.open(TestFragment.PATH)
        }
    }
}