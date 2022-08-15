package com.example.SOOJOOB.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.*
import com.example.SOOJOOB.databinding.FragmentCommunityBinding
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class CommunityFragment : Fragment() {

    private var fBinding : FragmentCommunityBinding? = null
    private lateinit var createBtn: ImageView

    fun work(){
        val service = RetrofitAPI.articleService

        service.getArticle()
            .enqueue(object: retrofit2.Callback<ArticleGetResponseBody>{
                override fun onResponse(
                    call: Call<ArticleGetResponseBody>,
                    response: Response<ArticleGetResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            it.result?.let { it1 -> setAdapter(it1) }
                        }
                    }
                }

                override fun onFailure(call: Call<ArticleGetResponseBody>, t: Throwable) {
                    Log.d("게시글  get 실패", t.message.toString())
                }
            })
    }

    private lateinit var recyler_view: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyler_view = itemView.findViewById(R.id.recyler_view)
        createBtn = itemView.findViewById(R.id.add_article_btn)
        createBtn.setOnClickListener {
            val intent = Intent(activity, ArticleInsertActivity::class.java)
            startActivity(intent)
        }
        work()
    }
//    override fun onDestroyView() {
//        fBinding = null
//        super.onDestroyView()
//    }

    private fun setAdapter(ArticleList : List<Article>){
        val mAdapter = ArticleAdapter( ArticleList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = LinearLayoutManager(activity)

        recyler_view.setHasFixedSize(false)
    }
}