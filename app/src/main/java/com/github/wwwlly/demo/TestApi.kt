package com.github.wwwlly.demo

import com.github.wwwlly.net.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET

interface TestApi {

    @GET
    fun getUserInfo(): Observable<HttpResult<String>>
}