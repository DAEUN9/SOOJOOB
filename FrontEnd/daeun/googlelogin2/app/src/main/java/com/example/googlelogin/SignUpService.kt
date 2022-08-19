package com.example.googlelogin

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("oauth/jwt/google")
    fun addUserByEnqueue(@Body userInfo: RequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

}