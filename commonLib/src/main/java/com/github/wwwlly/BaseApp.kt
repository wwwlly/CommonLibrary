package com.github.wwwlly

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter

open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("app", "on create")

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}