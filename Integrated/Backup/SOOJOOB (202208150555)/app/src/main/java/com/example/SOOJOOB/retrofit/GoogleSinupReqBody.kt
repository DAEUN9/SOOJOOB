package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName

data class GoogleSinupReqBody(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("googleId")
    val googleId: String?
)
