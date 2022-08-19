package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class UserupdateWork (private val userInfo: UserupdateRequestBody){

    fun work(completion: (statusCode:Int, msg:String?) -> Unit) {
        val service = RetrofitAPI.userupdateService

        val gson = Gson()
        val userupdateRequestBody = UserupdateRequestBody(userInfo.age, userInfo.email, userInfo.region, userInfo.username)
        val gsonObjectString = gson.toJson(userupdateRequestBody)

        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        service.userUpdateEnqueue(requestBody)
            .enqueue(object : retrofit2.Callback<UserupdateResponseBody> {
                override fun onResponse(
                    call: Call<UserupdateResponseBody>,
                    response: Response<UserupdateResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("회원정보 수정 성공", "$response.body()")
                        val result = response.body()?.msg
                        completion(response.code(), result)
                    } else {
                        Log.d("회원정보 수정 실패", "$response")
                        val result = response.errorBody()?.string()
                        completion(response.code(), result)

                    }
                }

                override fun onFailure(call: Call<UserupdateResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())
                }
            })
    }
}