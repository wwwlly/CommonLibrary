package com.github.wwwlly.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.github.wwwlly.R
import com.github.wwwlly.activity.base.BaseActivity

@Route(path = ARouterRootFragmentActivity.COMM_ROOT_FRAGMENT, group = "comm")
class ARouterRootFragmentActivity : BaseActivity() {

    @Autowired(name = "path")
    var path: String = ""

    @Autowired(name = "bundle")
    var bundle: Bundle? = null

    var fragment: Fragment? = null

    companion object {
        const val COMM_ROOT_FRAGMENT = "/comm/rootfragmentactivity"

        fun open(path: String, bundle: Bundle? = null) {
            val postCard = ARouter.getInstance().build(COMM_ROOT_FRAGMENT)
                .withString("path", path)
                .withBundle("bundle", bundle)
            postCard.navigation()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arouter_root_fragment)

        fragment =
            ARouter.getInstance().build(path).withBundle("bundle", bundle).navigation() as Fragment?

        fragment?.let {
            supportFragmentManager.beginTransaction().add(R.id.root_container, it, "root_fragment")
        }
    }
}