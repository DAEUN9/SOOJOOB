package com.example.SOOJOOB.retrofit

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

data class UserInfoResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: UserInfoData?
)


data class UserInfoData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("age")
    val age: Any,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("region")
    val region: Any,
    @SerializedName("weight")
    val weight: Any,
    @SerializedName("height")
    val height: Any,
)

interface UserInfoService {


    @Headers("Content-Type: application/json")
    @GET("user")
    fun userInfoEnqueue(): Call<UserInfoResponseBody> // Call 은 흐름처리 기능을 제공해줌

}

class UserInfoWork () {

    fun work(completion : (statusCode:Int, username:String?, email:String?, age:Any?, gender:Any?, region:Any?) -> Unit) {
        val service = RetrofitAPI.userinfoService

        service.userInfoEnqueue()
            .enqueue(object : retrofit2.Callback<UserInfoResponseBody> {
                override fun onResponse(
                    call: Call<UserInfoResponseBody>,
                    response: Response<UserInfoResponseBody>
                ){
                    if (response.isSuccessful) {
                        Log.d("통신 성공", "$response.body()")
                        val username = response.body()?.data?.username
                        val email = response.body()?.data?.email
                        val age = response.body()?.data?.age
                        val gender = response.body()?.data?.gender
                        val region = response.body()?.data?.region
                        completion(response.code(), "$username", "$email", "$age", "$gender", "$region")
                    }
                    else {
                        Log.d("통신 실패", "$response")
                    }
                }
                override fun onFailure(call: Call<UserInfoResponseBody>, t: Throwable) {
                    Log.d("응답 실패", t.message.toString())
                }
            })
    }
}



