package com.example.SOOJOOB.retrofit

import android.util.Log
import com.example.SOOJOOB.ArticleGetResponseBody
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class ArticleGetWork {

    fun work() {
        val service = RetrofitAPI.articleService


        service.getArticle()
            .enqueue(object : retrofit2.Callback<ArticleGetResponseBody> {
                override fun onResponse(
                    call: Call<ArticleGetResponseBody>,
                    response: Response<ArticleGetResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("타입 확인","${result?.result?.get(0)?.createdDate}")
                    }
                }

                override fun onFailure(call: Call<ArticleGetResponseBody>, t: Throwable) {
                    Log.d("게시글  get 실패", t.message.toString())
                }
            })
    }
}