package com.example.SOOJOOB.retrofit

import com.example.SOOJOOB.ArticleGetResponseBody
import com.example.SOOJOOB.ArticleRequestBody
import com.example.SOOJOOB.ArticleResponseBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ArticleService {

    @Headers("Content-Type: application/json")
    @POST("article")
    fun addArticleByEnqueue(@Body ArticleInfo: RequestBody): Call<ArticleResponseBody>

    @Headers("Content-Type: application/json")
    @GET("article")
    fun getArticle(): Call<ArticleGetResponseBody>


}