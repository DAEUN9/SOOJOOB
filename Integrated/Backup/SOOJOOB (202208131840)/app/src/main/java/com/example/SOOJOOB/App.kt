package com.example.SOOJOOB

import android.app.Application

class App : Application() {


    companion object{
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }

}