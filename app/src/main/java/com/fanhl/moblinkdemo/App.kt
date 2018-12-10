package com.fanhl.moblinkdemo

import android.app.Application
import com.mob.MobSDK

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MobSDK.init(this)
    }
}
