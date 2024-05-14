package com.github.wwwlly.ext

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(MyObserver<T>().apply(observer))
}