package com.github.wwwlly.net

interface HttpResult<T> {

    var data: T
    var message: String?

    fun isSuccess(): Boolean
}