package com.fanhl.moblinkdemo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mob.moblink.ActionListener
import com.mob.moblink.MobLink
import com.mob.moblink.Scene
import com.mob.moblink.SceneRestorable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SceneRestorable {

    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.apply {
            info.observe(this@MainActivity, Observer {
                et_info.setText(it)
            })
        }
        btn_create.setOnClickListener { create() }
    }

    /**
     * 必须重写该方法，防止MobLink在某些情景下无法还原
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        MobLink.updateNewIntent(getIntent(), this)
    }

    override fun onReturnSceneData(scene: Scene) {
        val info = scene.params["info"] as? String ?: return
        viewModel.info.value = info
    }

    private fun create() {
        val scene = Scene().apply {
            path = "/demo/a"
            params = hashMapOf(
                "info" to et_info.text.toString()
            )
        }

        MobLink.getMobID(scene, object : ActionListener<String> {
            override fun onResult(mobID: String?) {
                Log.d(TAG, "onResult: mobID:$mobID")
                share(mobID ?: return)
            }

            override fun onError(throwable: Throwable?) {
                Log.e(TAG, "onError: ", throwable)
            }
        })
    }

    private fun share(mobID: String) {
        et_url.setText("http://a2au.t4m.cn/$mobID")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    class ViewModel(app: Application) : AndroidViewModel(app) {
        var info = MutableLiveData<String>()
    }
}
