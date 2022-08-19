package com.example.proto04


import com.example.proto04.retrofit.BadgesResponseBody
import com.example.proto04.retrofit.PloggingResponseBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun addUserByEnqueue(@Body userInfo: RequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

    @GET("badges/{userId}")
    fun getUserBadge(@Path("userId") userId: String):Call<BadgesResponseBody>

    @GET("badges/{userId}/no")
    fun getNoBadges(@Path("userId") userId: String):Call<BadgesResponseBody>

    @GET("plogging/all")
    fun getMyPloggings():Call<PloggingResponseBody>

}