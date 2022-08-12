package com.example.SOOJOOB.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {


    @Headers("Content-Type: application/json")
    @GET("record")
    fun addUserByEnqueue(): Call<UserResponseBody> // Call 은 흐름처리 기능을 제공해줌

}