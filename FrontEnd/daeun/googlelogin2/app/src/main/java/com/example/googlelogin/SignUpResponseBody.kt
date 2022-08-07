package com.example.googlelogin

import com.google.gson.annotations.SerializedName

data class SignUpResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("jwtToken")
    val jwtToken: String?
) {
    class User (
        val id: Int?,
        val email: String?,
        val username: String?,
        val role: String?,
        val gender: String?,
        val age: String?,
        val weight: String?,
        val height: String?,
        val region: String?

        )
}