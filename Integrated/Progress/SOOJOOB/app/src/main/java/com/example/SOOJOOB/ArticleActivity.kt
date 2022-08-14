package com.example.SOOJOOB

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.databinding.ActivityArticleBinding
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ArticleActivity : AppCompatActivity() {
    private var res: List<Any>? = null
    private lateinit var recyler_view: RecyclerView
    private lateinit var title:TextView
    private lateinit var contents:TextView
    private lateinit var dateTime:TextView
    private lateinit var imageView:ImageView
    private lateinit var uploadImageBtn:Button

    private lateinit var binding: ActivityArticleBinding

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
//    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        imageView.load(uri)
//    }

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
    val Gallery = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyler_view = findViewById(R.id.recyler_view)
        imageView = findViewById(R.id.image)
        uploadImageBtn = findViewById(R.id.uploadImageBtn)

        // 앨범 버튼 클릭 리스너 구현
//        uploadImageBtn.setOnClickListener{
//            loadImage()
//        }

        checkPermission.launch(permissionList)

//        uploadImageBtn.setOnClickListener {
//            readImage.launch("image/*")
//        }
        this.work()
        println("게시판 데이터 get 호출!!")

        binding.addArticleBtn.setOnClickListener{
            val intent = Intent(this, ArticleInsertActivity::class.java)
            startActivity(intent)
        }



    }

    private fun setAdapter(ArticleList : List<Article>){
        val mAdapter = ArticleAdapter( ArticleList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = GridLayoutManager(this,1, GridLayoutManager.VERTICAL,
            false)

        recyler_view.setHasFixedSize(false)
    }

    private fun loadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"load picture"),Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Gallery) {
            if(resultCode == RESULT_OK){
                var dataUri = data?.data
                try{
                    var bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,dataUri)
                    imageView.setImageBitmap(bitmap)
                }catch (e:Exception){
                    Toast.makeText(this,"$e", Toast.LENGTH_SHORT).show()
                }
            }

            }
        }

}