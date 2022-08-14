package com.example.SOOJOOB

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityArticleInsertBinding
import com.example.SOOJOOB.retrofit.ArticleWork
import java.text.SimpleDateFormat
import java.util.*

class ArticleInsertActivity  : AppCompatActivity(){

    private val binding by lazy {
        ActivityArticleInsertBinding.inflate(layoutInflater)
    }

    private lateinit var title: TextView
    private lateinit var contents: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnWrite.setOnClickListener{

            val articleData = ArticleRequestBody(
                binding.title.text.toString(),
                binding.contents.text.toString()
            )
            val insertWork = ArticleWork(articleData)
            val intent = Intent(this, ArticleActivity::class.java)
            insertWork.work(completion = { status, msg ->
                if (status in 200..300){
                    Toast.makeText(this@ArticleInsertActivity, "$msg", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }else{
                    val msg = msg?.substring(25 until 37)
                    Toast.makeText(this@ArticleInsertActivity, "$msg", Toast.LENGTH_SHORT).show()

                }
            } )
        }

        binding.btnCancel.setOnClickListener{
            super.onBackPressed()
        }
    }
}