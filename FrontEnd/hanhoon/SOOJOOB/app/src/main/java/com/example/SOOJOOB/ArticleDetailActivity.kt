package com.example.SOOJOOB

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.SOOJOOB.databinding.ActivityArticleDetailBinding
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class ArticleDetailActivity : AppCompatActivity() {
    private var id = 1
    lateinit var datas : Article
    private val binding by lazy {
        ActivityArticleDetailBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        println("게시글 상세 보기")

        val ti = intent.getStringExtra("title")
        val co = intent.getStringExtra("contents")
        val cd = intent.getStringExtra("createdDate")
        val uN = intent.getStringExtra("userName")
        val im = intent.getByteArrayExtra("articleImage")

        binding.title.text = ti.toString()
        binding.contents.text = co.toString()
        binding.createdDate.text = cd.toString()
        binding.userName.text = uN.toString()
        binding.image.setImageBitmap(im?.toBitmap())

        binding.backtolist.setOnClickListener{
            super.onBackPressed()
        }
    }


    fun String.toBitmap(): Bitmap?{
        Base64.decode(this, Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }
    }

    fun ByteArray.toBitmap():Bitmap{
        return BitmapFactory.decodeByteArray(this,0,size)
    }
}



