package com.github.wwwlly.demo

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.wwwlly.demo.databinding.FragmentTestBinding
import com.github.wwwlly.fragment.BaseVmVbFragment

@Route(path = TestFragment.PATH)
class TestFragment : BaseVmVbFragment<TestViewModel, FragmentTestBinding>() {

    companion object {
        const val PATH = "/demo/fragment/test"
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