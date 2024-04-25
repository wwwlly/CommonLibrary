package com.github.wwwlly

import android.app.Application
import android.util.Log

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("app", "on create")
    }
}