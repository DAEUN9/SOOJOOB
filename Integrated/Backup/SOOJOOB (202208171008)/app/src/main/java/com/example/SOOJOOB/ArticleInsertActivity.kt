package com.example.SOOJOOB

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityArticleInsertBinding
import com.example.SOOJOOB.fragments.CommunityFragment
import com.example.SOOJOOB.retrofit.ArticleWork
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ArticleInsertActivity  : AppCompatActivity(){
    private lateinit var imageView: ImageView
    private lateinit var uploadImageBtn: ImageView
    val Gallery = 0
    private lateinit var bitmap:Bitmap



    private val binding by lazy {
        ActivityArticleInsertBinding.inflate(layoutInflater)
    }

    private lateinit var title: TextView
    private lateinit var contents: TextView

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_park)

        binding.btnWrite.setOnClickListener{

            val articleData = ArticleRequestBody(
                binding.title.text.toString(),
                binding.contents.text.toString(),
                encodeImage(bitmap),

                )
            val insertWork = ArticleWork(articleData)
            val intent = Intent(this, CommunityFragment::class.java)
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
        binding.backCommunity.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnCancel.setOnClickListener{
            super.onBackPressed()
        }

        binding.uploadImageBtn.setOnClickListener {
            loadImage()
        }


        checkPermission.launch(permissionList)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == RESULT_OK){
            val dataUri = data?.data
            try{
                bitmap= MediaStore.Images.Media.getBitmap(this.contentResolver,dataUri)
                binding.uploadImageBtn.setImageBitmap(bitmap)
                println("성공")
            }catch (e: Exception){
                Toast.makeText(this,"$e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"load picture"),Gallery)
    }
    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


}