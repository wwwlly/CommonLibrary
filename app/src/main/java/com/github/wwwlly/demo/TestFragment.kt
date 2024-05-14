package com.github.wwwlly.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.wwwlly.fragment.BaseVmFragment

@Route(path = TestFragment.PATH)
class TestFragment : BaseVmFragment<TestViewModel>() {

    companion object {
        const val PATH = "/demo/fragment/test"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        view.findViewById<Button>(R.id.btn_okhttp).setOnClickListener {
            mViewModel.getUserInfo()
        }

        mViewModel.userInfo.observe {
            onSuccess {

            }

            onError {

            }
        }
    }
}