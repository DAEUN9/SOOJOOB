package com.example.proto04.retrofit

import com.google.gson.annotations.SerializedName

data class PloggingResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: List<Plogging>?
)
