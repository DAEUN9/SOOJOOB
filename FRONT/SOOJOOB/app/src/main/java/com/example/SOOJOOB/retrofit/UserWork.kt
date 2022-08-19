package com.example.SOOJOOB.retrofit


import android.util.Log
import retrofit2.Call
import retrofit2.Response

class UserWork() {
    fun work(completion : (statusCode:Int, userid: Int?, username:String?, trash:Int?, exp:Double?, badge:Int?) -> Unit) {
        val service = RetrofitAPI.userService


//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.addUserByEnqueue()
            .enqueue(object : retrofit2.Callback<UserResponseBody> {
                override fun onResponse(
                    call: Call<UserResponseBody>,
                    response: Response<UserResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("통신 성공", "$response.body()")
                        val userid = response.body()?.data?.userRecord?.id
                        val username = response.body()?.data?.userRecord?.username
                        val trash = response.body()?.data?.totalTrashCount
                        val exp = response.body()?.data?.exp
                        val badge = response.body()?.data?.badgeCount

                        completion(response.code(), userid,"$username", trash, exp, badge)
                    }
                    else {
                        Log.d("통신 실패", "$response")
                    }
                }

                override fun onFailure(call: Call<UserResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())
                }
            })
    }
}