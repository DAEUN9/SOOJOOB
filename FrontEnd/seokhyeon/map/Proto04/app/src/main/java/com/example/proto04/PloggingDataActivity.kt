package com.example.proto04

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

open class PloggingDataActivity : AppCompatActivity() {

    private var res: List<Any>? = null
    private lateinit var recyler_view:RecyclerView
    private lateinit var date:TextView
    private lateinit var distance:TextView
    private lateinit var recTime:TextView
    private lateinit var trashCnt:TextView

    private lateinit var imageString:String
    private lateinit var image:ImageView
//    private lateinit var imageBitmap:Bitmap

    fun String.toBitmap(): Bitmap?{
        Base64.decode(this,Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }
    }

    fun work(){
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
                        result?.let {
                            it.result?.let { it1 -> setAdapter(it1) }
                        }
//                        Log.d("플로깅 get 성공", "${result}")
//                        Log.d("타입 확인","${result?.result?.get(2)?.dateTime}")
//                        getText.text = "플로깅 날짜 : " + result?.result?.get(2)?.dateTime + "\n플로깅 시간 :  " + result?.result?.get(2)?.timeRecord + "\n쓰레기 수 :" + result?.result?.get(2)?.trashCount + "\n 플로깅 거리 : "  + result?.result?.get(2)?.distance
//                        date.text = result?.result?.get(2)?.dateTime
//                        distance.text = result?.result?.get(2)?.distance.toString()
//                        recTime.text = result?.result?.get(2)?.timeRecord.toString()
//                        trashCnt.text = result?.result?.get(2)?.trashCount.toString()
////                        println("이미지 타입" + result?.result?.get(2)?.ploggingImg?.javaClass)
//                        imageString = result?.result?.get(2)?.ploggingImg as String
//                        image.setImageBitmap(imageString.toBitmap())
//                        res = result?.result


                    }
                }

                override fun onFailure(call: Call<PloggingGetResponseBody>, t: Throwable) {
                    Log.d("플로깅  get 실패", t.message.toString())
                }
            })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec)

        recyler_view = findViewById(R.id.recyler_view)
//        val mAdapter = PloggingAdapter(this, res)
//        RecyclerView.adapter = mAdapter
//
//        val lm = LinearLayoutManager(this)
//        mRecyclerView.layoutManager = lm
//        mRecyclerView.setHasFixedSize(true)

//        date = findViewById(R.id.date)
//        image = findViewById(R.id.image)
//        recTime = findViewById(R.id.recTime)
//        trashCnt = findViewById(R.id.trashCnt)
//        distance = findViewById(R.id.distance)
        this.work()
        println("플로깅 데이터 get 호출!!")

    }

    private fun setAdapter(PloggingList : List<Result>){
        val mAdapter = PloggingAdapter(this, PloggingList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,
            false)

        recyler_view.setHasFixedSize(false)
    }


}