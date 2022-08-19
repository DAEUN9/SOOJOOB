package com.example.SOOJOOB.retrofit


import com.google.gson.annotations.SerializedName

data class UserupdateRequestBody(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("email")
    val email: String?,
//    @SerializedName("gender")
//    val gender: String?,
//    @SerializedName("height")
//    val height: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("username")
    val username: String?,
//    @SerializedName("weight")
//    val weight: String?
)