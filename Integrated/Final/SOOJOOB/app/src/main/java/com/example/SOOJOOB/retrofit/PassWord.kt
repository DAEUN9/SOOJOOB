package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

class PasswordResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Any?
)

data class PasswordRequestBody(
    @SerializedName("password")
    val password: String?
)

interface PasswordService {
    @Headers("Content-Type: application/json")
    @PUT("user/change/pw")
    fun passWordEnqueue(
        @Body password: RequestBody
    ): Call<PasswordResponseBody>
}

class PassWordWork (private val password:PasswordRequestBody) {

    fun work(completion: (statusCode:Int, msg:String?) -> Unit) {
        val service = RetrofitAPI.passwordService

        val gson = Gson()
        val passwordRequestBody = PasswordRequestBody(password.password)
        val gsonObjectString = gson.toJson(passwordRequestBody)

        val requestBody = gsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        service.passWordEnqueue(requestBody)
        .enqueue(object : retrofit2.Callback<PasswordResponseBody> {
            override fun onResponse(
                call: Call<PasswordResponseBody>,
                response: Response<PasswordResponseBody>
            ) {
                if (response.isSuccessful) {
                    Log.d("비밀번호 수정 성공", "$response.body()")
                    val result = response.body()?.msg
                    completion(response.code(), result)
                } else {
                    Log.d("비밀번호 수정 실패", "$response")
                    val result = response.errorBody()?.string()
                    completion(response.code(), result)

                }
            }

            override fun onFailure(call: Call<PasswordResponseBody>, t: Throwable) {
                Log.d("응답 실패", t.message.toString())
            }
        })
}
}