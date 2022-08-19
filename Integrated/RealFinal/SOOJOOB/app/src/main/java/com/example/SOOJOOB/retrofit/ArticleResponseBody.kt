package com.example.SOOJOOB

import com.google.gson.annotations.SerializedName

data class ArticleResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Any?
)

