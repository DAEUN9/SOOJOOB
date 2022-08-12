package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class PloggingWork(private val ploggingInfo: PloggingRequestBody) {
    fun work() {
        val service = RetrofitAPI.ploggingService

        // gson.toJson 을 이용해서 Json 으로 변경
        val gson = Gson()
        val ploggingRequestBody = PloggingRequestBody(ploggingInfo.distance, ploggingInfo.timeRecord, ploggingInfo.trashCount, ploggingInfo.dateTime, ploggingInfo.ploggingImg)
        val gsonObjectString = gson.toJson(ploggingRequestBody)


        // RequestBody 생성
        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.addPloggingByEnqueue(requestBody)
            .enqueue(object : retrofit2.Callback<PloggingResponseBody> {
                override fun onResponse(
                    call: Call<PloggingResponseBody>,
                    response: Response<PloggingResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("플로깅  성공", "$result")
                    }
                }

                override fun onFailure(call: Call<PloggingResponseBody>, t: Throwable) {
                    Log.d("플로깅 실패", t.message.toString())
                }
            })



    }
}