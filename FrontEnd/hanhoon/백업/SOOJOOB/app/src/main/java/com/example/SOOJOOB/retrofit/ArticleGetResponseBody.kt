package com.example.SOOJOOB

import com.google.gson.annotations.SerializedName

data class ArticleGetResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val result: List<Article>?
)

data class Article(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("createdDate")
    val createdDate: String?
)

