package com.github.wwwlly.net

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.nio.charset.Charset


/**
 * Created by wangkp on 2018/11/14.
 */
object RetrofitHelper {
    val mBuilder: Retrofit.Builder = Retrofit.Builder()
//        .addConverterFactory(GsonConverter())

        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpUtils.getInstance().okHttpClient.newBuilder()
//            .addInterceptor { chain ->
//                val req = chain.request()
//                var url = req.url()
//                if (req.method() == "GET" && url.queryParameter("sign").isNullOrEmpty() && url.querySize() > 0) {
//                    url = url.newBuilder().addQueryParameter("sign", MD5.getMD5("?${url.encodedQuery()}" + AppConstants.MD5)).build()
//                }
//                chain.proceed(req.newBuilder().url(url).build()).apply {
//                    try {
//                        logNet(this)
//                    } catch (e: Exception) {
//                    }
//                }
//            }
            .build())

    inline fun <reified T> create(baseUrl: String): T {
        return mBuilder.baseUrl(baseUrl).build().create(T::class.java)
    }

    fun <T> create(baseUrl: String, service: Class<T>): T {
        return mBuilder.baseUrl(baseUrl).build().create(service)
    }

}

open class MyObserver<T> : Observer<T> {

    var onNext: ((T) -> Unit)? = null
    var onComplete: (() -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
    var onSubscribe: ((Disposable) -> Unit)? = null

    fun onNext(l: (T) -> Unit) {
        onNext = l
    }

    fun onComplete(l: () -> Unit) {
        onComplete = l
    }

    fun onError(l: (Throwable) -> Unit) {
        onError = l

    }

    fun onSubscribe(l: (Disposable) -> Unit) {
        onSubscribe = l
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
        onComplete()
    }

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

}

fun <T> Observable<T>.observer(observer: MyObserver<T>.() -> Unit) {
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver<T>().apply(observer))
}
