package com.example.SOOJOOB


import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.databinding.ActivityMyArticleBinding
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class MyArticleActivity : AppCompatActivity() {
    private lateinit var recyler_view: RecyclerView
    ////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    private lateinit var lastestBtn: Button
    private lateinit var pastBtn: Button
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
                        val result = response.body()?.result?.reversed()
                        result?.let {
                            it.let { it1 -> setAdapter(it1) }
                        }

                        ////////////////////////////////////////////////////
                        /////////////////////////////////////////////////////
                        lastestBtn.setOnClickListener {
                            val lastest = response.body()?.result?.reversed()
                            lastest?.let {
                                it.let{it1 -> setAdapter(it1)}
                            }
                        }

                        pastBtn.setOnClickListener {
                            val past = response.body()?.result
                            past?.let {
                                it.let{it1 -> setAdapter(it1)}
                            }
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

        lastestBtn = findViewById(R.id.lastestSort)
        pastBtn = findViewById(R.id.pastSort)


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
