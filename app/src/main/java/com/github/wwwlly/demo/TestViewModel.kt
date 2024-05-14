package com.github.wwwlly.demo

import com.github.wwwlly.base.BaseViewModel
import com.github.wwwlly.net.StatusLiveData

class TestViewModel: BaseViewModel<TestApi>("http://www.baidu.com") {

    val userInfo = StatusLiveData<String>()

    fun getUserInfo() {
        mApi.getUserInfo().observer(userInfo)
    }
}