package com.example.proto04

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream

open class PloggingDataActivity : AppCompatActivity() {

    private lateinit var getText:TextView
    private lateinit var imageString:String
    private lateinit var image:ImageView
//    private lateinit var imageBitmap:Bitmap

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun String.toBitmap(): Bitmap?{
        Base64.decode(this,Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }
    }

    fun work() {
        val service = RetrofitAPI.ploggingService

        // gson.toJson 을 이용해서 Json 으로 변경


//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.

        service.getPlogging()
            .enqueue(object : retrofit2.Callback<PloggingGetResponseBody> {
                override fun onResponse(
                    call: Call<PloggingGetResponseBody>,
                    response: Response<PloggingGetResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
//                        Log.d("플로깅 get 성공", "${result}")
                        Log.d("타입 확인","${result?.result?.get(0)?.dateTime}")
                        getText.text = "플로깅 날짜 : " + result?.result?.get(0)?.dateTime + "\n플로깅 시간 :  " + result?.result?.get(0)?.timeRecord + "\n쓰레기 수 :" + result?.result?.get(0)?.trashCount + "\n 플로깅 거리 : "  + result?.result?.get(0)?.distance
                        println("이미지 타입" + result?.result?.get(0)?.ploggingImg?.javaClass)
                        imageString = result?.result?.get(0)?.ploggingImg as String
                        image = findViewById(R.id.image)
                        image.setImageBitmap(imageString.toBitmap())

                    }
                }

                override fun onFailure(call: Call<PloggingGetResponseBody>, t: Throwable) {
                    Log.d("플로깅  get 실패", t.message.toString())
                }
            })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plogging_data)


        getText = findViewById(R.id.data)

        this.work()
        println("플로깅 데이터 get 호출!!")

//        image.setImageBitmap(bitmap)
    }



}