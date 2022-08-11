package com.example.SOOJOOB.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {


    @Headers(
        "Content-Type: application/json",
        "X-AUTH-TOKEN: eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJzc2FmeTFAbmF2ZXIuY29tIiwiZW1haWwiOiJzc2FmeTFAbmF2ZXIuY29tIiwiaWF0IjoxNjYwMjU0MTU5LCJleHAiOjE2NjAyNjg1NTl9.K7AAi6rAT2kKsiPvsjqB81jelpXguuqKdA3ZT5XBPDQ")
    @GET("record")
    fun addUserByEnqueue(): Call<UserResponseBody> // Call 은 흐름처리 기능을 제공해줌

}