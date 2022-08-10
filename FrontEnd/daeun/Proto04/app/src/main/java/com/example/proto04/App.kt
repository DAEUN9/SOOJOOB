package com.example.proto04

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