package com.example.googlelogin

import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("googleId")
    val googleId: String?
)
