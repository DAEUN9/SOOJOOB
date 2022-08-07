package com.example.googlelogin

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RetrofitWork() {
    fun work(userInfo: SignUpRequestBody) {
        val service = RetrofitAPI.emgMedService

        // gson.toJson 을 이용해서 Json 으로 변경
        val gson = Gson()
        val signUpRequestBody = SignUpRequestBody(userInfo.email, userInfo.name, userInfo.googleId)
        val gsonObjectString = gson.toJson(signUpRequestBody)


        // RequestBody 생성
        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        println(requestBody)

//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.addUserByEnqueue(requestBody)
            .enqueue(object : retrofit2.Callback<SignUpResponseBody> {
                override fun onResponse(
                    call: Call<SignUpResponseBody>,
                    response: Response<SignUpResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("회원가입 성공", "$result")
                    }
                }

                override fun onFailure(call: Call<SignUpResponseBody>, t: Throwable) {
                    Log.d("회원가입 실패", t.message.toString())
                }
            })
    }
}