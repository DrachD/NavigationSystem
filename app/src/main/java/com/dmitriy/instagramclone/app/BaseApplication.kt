package com.dmitriy.instagramclone.app

import android.app.Application

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Singleton.init(applicationContext)
    }
}