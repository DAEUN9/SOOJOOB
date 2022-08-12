package com.example.SOOJOOB.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BadgeService {

    @GET("badges/{userId}")
    fun getUserBadge(@Path("userId") userId: String): Call<BadgesResponseBody>

    @GET("badges/{userId}/no")
    fun getNoBadges(@Path("userId") userId: String): Call<BadgesResponseBody>
}