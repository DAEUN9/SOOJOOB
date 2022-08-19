package com.example.SOOJOOB.retrofit


import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface UserupdateService {

    @Headers("Content-Type: application/json")
    @PUT("user/update")
    fun userUpdateEnqueue(
        @Body userInfo: RequestBody
    ): Call<UserupdateResponseBody>
}