package com.example.SOOJOOB

import com.google.gson.annotations.SerializedName

data class PloggingResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Any?
)

