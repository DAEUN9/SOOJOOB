package com.example.SOOJOOB

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface PloggingService {


    @POST("plogging")
    fun addPloggingByEnqueue(@Body ploggingInfo: RequestBody): Call<PloggingResponseBody> // Call 은 흐름처리 기능을 제공해줌



    @GET("plogging/all")
    fun getPlogging(): Call<PloggingGetResponseBody> // Call 은 흐름처리 기능을 제공해줌

}