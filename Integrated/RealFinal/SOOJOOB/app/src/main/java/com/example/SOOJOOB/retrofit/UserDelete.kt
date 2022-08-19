package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Headers


data class UserDeleteResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Any?
)


interface UserDeleteService {
    @Headers("Content-Type: application/json")
    @DELETE("user")
    fun userDelete(): Call<UserDeleteResponseBody>
}


class UserDeleteWork {
    fun work(completion: (statusCode:Int, msg:String?) -> Unit) {
        val service = RetrofitAPI.userDeleteService

        service.userDelete()
            .enqueue(object : retrofit2.Callback<UserDeleteResponseBody> {
                override fun onResponse(
                    call: Call<UserDeleteResponseBody>,
                    response: Response<UserDeleteResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("회원탈퇴 성공", "$response.body()")
                        val result = response.body()?.msg
                        completion(response.code(), result)
                    } else {
                        Log.d("회원탈퇴 실패", "$response")
                        val result = response.errorBody()?.string()
                        completion(response.code(), result)
                    }
                }
                override fun onFailure(call: Call<UserDeleteResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())
                }
            })

    }
}