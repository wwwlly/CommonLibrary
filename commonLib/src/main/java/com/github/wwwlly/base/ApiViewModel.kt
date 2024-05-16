package com.github.wwwlly.base

import android.util.Log
import com.github.wwwlly.net.*
import io.reactivex.Observable
import java.lang.reflect.ParameterizedType

open class ApiViewModel<T>(val url: String) : BaseViewModel() {

    protected val mApi: T by lazy {
        RetrofitHelper.create(url, getTClass())
    }

    private fun getTClass(): Class<T> {
        return (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    fun <T> Observable<HttpResult<T>>.observer(
        liveData: StatusLiveData<T>,
        t: (StatusLiveData<T>.(T) -> Unit)? = null
    ) {
        observer {
            onNext {
                if (it.isSuccess()) {
                    if (t == null) {
                        liveData.setData(it.data)
                    } else {
                        liveData.t(it.data)
                    }
                } else {
                    onError?.invoke(StatusThrowable(it.message))
                }
            }
            onError {
                Log.d("base view model", it.message.toString())
            }
        }
    }
}