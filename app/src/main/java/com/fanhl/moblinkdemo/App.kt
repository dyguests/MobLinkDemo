package com.fanhl.moblinkdemo

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.mob.MobSDK
import com.mob.moblink.MobLink
import com.mob.moblink.RestoreSceneListener
import com.mob.moblink.Scene

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        MobSDK.init(this)
        MobLink.setRestoreSceneListener(object : RestoreSceneListener {
            override fun completeRestore(scene: Scene?) {
                Log.d(TAG, "completeRestore: ")
//                Toast.makeText(this@App, "completeRestore", Toast.LENGTH_LONG).show()
            }

            override fun willRestoreScene(scene: Scene?): Class<out Activity> {
                Log.d(TAG, "willRestoreScene: ")
//                Toast.makeText(this@App, "willRestoreScene", Toast.LENGTH_LONG).show()
                return MainActivity::class.java
            }

            override fun notFoundScene(scene: Scene?) {
                Log.d(TAG, "notFoundScene: ")
//                Toast.makeText(this@App, "notFoundScene", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private val TAG = App::class.java.simpleName
    }
}
