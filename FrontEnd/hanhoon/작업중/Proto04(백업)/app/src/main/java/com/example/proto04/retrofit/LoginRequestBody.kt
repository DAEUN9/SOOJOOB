package com.example.proto04

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)
