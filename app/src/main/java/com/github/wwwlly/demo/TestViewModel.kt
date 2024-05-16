package com.github.wwwlly.demo

import com.github.wwwlly.base.ApiViewModel
import com.github.wwwlly.net.StatusLiveData

class TestViewModel: ApiViewModel<TestApi>("http://www.baidu.com") {

    val userInfo = StatusLiveData<String>()

    fun getUserInfo() {
        mApi.getUserInfo().observer(userInfo)
    }
}