package com.example.SOOJOOB.retrofit

import com.example.SOOJOOB.ArticleRequestBody
import android.util.Log
import com.example.SOOJOOB.ArticleResponseBody
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class ArticleWork(private val ArticleInfo: ArticleRequestBody) {
    fun work(completion : (statusCode:Int, msg:String?) -> Unit){
        val service = RetrofitAPI.articleService

        val gson = Gson()
        val articleRequestBody = ArticleRequestBody(ArticleInfo.title, ArticleInfo.contents, ArticleInfo.articleImage)
        val gsonObjectString = gson.toJson(articleRequestBody)


        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        service.addArticleByEnqueue(requestBody)
            .enqueue(object  : retrofit2.Callback<ArticleResponseBody>{
                override fun onResponse(
                    call: Call<ArticleResponseBody>,
                    response: Response<ArticleResponseBody>
                ) {
                    if(response.isSuccessful){
                        val result = response.body()?.msg
                        Log.d("게시글 등록 성공", "$response.body()")
                        completion(response.code(), result)
                    }else{
                        val result = response.errorBody()?.string()
                        Log.d("게시글 등록 성공", "$response")
                        completion(response.code(), result)
                    }
                }

                override fun onFailure(call: Call<ArticleResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())

                }
            })
    }
}