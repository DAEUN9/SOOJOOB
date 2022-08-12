package com.example.SOOJOOB

import com.example.SOOJOOB.retrofit.Badge
import com.google.gson.annotations.SerializedName

data class PloggingResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: List<Badge>?
)

