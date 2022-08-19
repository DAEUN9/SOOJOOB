package com.example.SOOJOOB

import com.google.gson.annotations.SerializedName

data class ArticleRequestBody(

    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("articleImage")
    val articleImage:String?,

    )