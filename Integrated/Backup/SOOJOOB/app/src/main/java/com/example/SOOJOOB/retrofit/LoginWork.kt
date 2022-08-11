package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class LoginWork(private val userInfo: LoginRequestBody ) {
    fun work(completion : (statusCode:Int) -> Unit) {
        val service = RetrofitAPI.loginService

        // gson.toJson 을 이용해서 Json 으로 변경
        val gson = Gson()
        val loginRequestBody = LoginRequestBody(userInfo.email, userInfo.password)
        val gsonObjectString = gson.toJson(loginRequestBody)


        // RequestBody 생성
        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.addLoginByEnqueue(requestBody)
            .enqueue(object : retrofit2.Callback<LoginResponseBody> {
                override fun onResponse(
                    call: Call<LoginResponseBody>,
                    response: Response<LoginResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("로그인 성공", "$response.body()")
                    }
                    else {
                        Log.d("로그인 실패", "$response")
                    }
                    completion(response.code())
                }

                override fun onFailure(call: Call<LoginResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())
                }
            })
    }
}