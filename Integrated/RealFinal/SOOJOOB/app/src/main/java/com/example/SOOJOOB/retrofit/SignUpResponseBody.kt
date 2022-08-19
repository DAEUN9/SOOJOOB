package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName

data class SignUpResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: String?
)