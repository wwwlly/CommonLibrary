package com.github.wwwlly.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.github.wwwlly.net.StatusLiveData
import java.lang.reflect.ParameterizedType

open class BaseVmFragment<VM : ViewModel> : BaseFragment() {

    protected val mViewModel: VM by lazy { ViewModelProviders.of(this).get(getVMClass()) }

    private fun getVMClass(): Class<VM> {
        return (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
    }

    fun <T> StatusLiveData<T>.observe(observer: StatusLiveData.Resource<T>.() -> Unit) {
        observe({ lifecycle }, { it?.observer() })
    }
}