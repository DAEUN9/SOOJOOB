package com.example.SOOJOOB.retrofit

import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.SOOJOOB.App
import com.example.SOOJOOB.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LogoutWork {

    fun logout() {
        App.prefs.setString("X-AUTH-TOKEN", "")


    }
}