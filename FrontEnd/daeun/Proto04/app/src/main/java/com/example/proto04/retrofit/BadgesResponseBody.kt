package com.example.proto04.retrofit

import com.example.proto04.Data
import com.google.gson.annotations.SerializedName

data class BadgesResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: List<Badge>?

)
