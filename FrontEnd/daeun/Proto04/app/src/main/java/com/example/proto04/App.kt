package com.example.proto04

import android.app.Application

class App : Application() {


    companion object{
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}