package com.example.SOOJOOB.retrofit

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun addLoginByEnqueue(@Body userInfo: RequestBody): Call<LoginResponseBody> // Call 은 흐름처리 기능을 제공해줌

}