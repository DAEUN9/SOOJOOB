package com.example.SOOJOOB


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.*
import com.example.SOOJOOB.databinding.ActivityArticleBinding
import com.example.SOOJOOB.databinding.ActivityMyArticleBinding
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class MyArticleActivity : AppCompatActivity() {
    private lateinit var recyler_view: RecyclerView
    private  var userId = 1

//    private lateinit var binding: ActivityArticleBinding
    private lateinit var binding: ActivityMyArticleBinding

    fun work() {
        val service = RetrofitAPI.articleService
        userId = intent.getIntExtra("userId",0)
        service.getUserArticle(userId)
            .enqueue(object : retrofit2.Callback<ArticleGetResponseBody> {
                override fun onResponse(
                    call: Call<ArticleGetResponseBody>,
                    response: Response<ArticleGetResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            it.result?.reversed()?.let { it1 -> setAdapter(it1) }
                        }
                    }
                }

                override fun onFailure(call: Call<ArticleGetResponseBody>, t: Throwable) {
                    Log.d("게시글  get 실패", t.message.toString())
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyler_view = findViewById(R.id.recyler_view)

        this.work()
        println("게시판 데이터 get 호출!!")


        binding.backMypage.setOnClickListener{
            super.onBackPressed()
        }


    }

    private fun setAdapter(ArticleList: List<Article>) {
        val mAdapter = ArticleAdapter(ArticleList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = GridLayoutManager(
            this, 1, GridLayoutManager.VERTICAL,
            false
        )

        recyler_view.setHasFixedSize(false)
    }
}
