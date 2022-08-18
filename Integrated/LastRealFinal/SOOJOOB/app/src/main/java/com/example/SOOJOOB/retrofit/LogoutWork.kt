package com.example.SOOJOOB.retrofit

import com.example.SOOJOOB.App

class LogoutWork {

    fun logout() {
        App.prefs.setString("X-AUTH-TOKEN", "")


    }
}