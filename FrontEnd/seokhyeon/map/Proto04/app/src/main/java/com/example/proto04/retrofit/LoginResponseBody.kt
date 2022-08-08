package com.example.proto04

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Data?
)

data class Data(
    @SerializedName("user")
    val user: User?,
    @SerializedName("jwtToken")
    val jwtToken: String?
)

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("age")
    val age: Any?,
    @SerializedName("weight")
    val weight: Any?,
    @SerializedName("height")
    val height: Any?,
    @SerializedName("region")
    val region: Any?
)