package com.example.SOOJOOB.retrofit

import android.util.Log
import com.example.SOOJOOB.*
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class GoogleSignupWork() {
    fun work(userInfo: GoogleSinupReqBody) {
        val service = RetrofitAPI.loginService

        // gson.toJson 을 이용해서 Json 으로 변경
        val gson = Gson()
        val signUpRequestBody = GoogleSinupReqBody(userInfo.email, userInfo.name, userInfo.googleId)
        val gsonObjectString = gson.toJson(signUpRequestBody)


        // RequestBody 생성
        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        println(requestBody)
        App.prefs.setString("X-AUTH-TOKEN", "")
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.addGoogleByEnqueue(requestBody)
            .enqueue(object : retrofit2.Callback<LoginResponseBody> {
                override fun onResponse(
                    call: Call<LoginResponseBody>,
                    response: Response<LoginResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("구글 로그인 성공", "$result")
                        val token = response.body()?.data?.jwtToken
                        Log.d("jwtToken", "$token")
                        if (token != null) {
                            App.prefs.setString("X-AUTH-TOKEN", token)
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponseBody>, t: Throwable) {
                    Log.d("구글 회원가입 실패", t.message.toString())
                }
            })
    }
}