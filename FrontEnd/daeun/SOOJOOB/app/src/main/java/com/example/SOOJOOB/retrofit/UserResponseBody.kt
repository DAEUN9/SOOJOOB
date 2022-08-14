package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName

data class UserResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: UserData?
)

data class UserData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("userRecord")
    val userRecord: UserRecord?,
    @SerializedName("totalDistance")
    val totalDistance: Double?,
    @SerializedName("totalTrashCount")
    val totalTrashCount: Int?,
    @SerializedName("totalTimeRecord")
    val totalTimeRecord: Int?,
    @SerializedName("exp")
    val exp: Double?,
    @SerializedName("badgeCount")
    val badgeCount: Int?
)

data class UserRecord(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("age")
    val age: Any,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("region")
    val region: Any,
    @SerializedName("weight")
    val weight: Any,
    @SerializedName("height")
    val height: Any,
    @SerializedName("activated")
    val activated: Boolean,
    @SerializedName("provider")
    val provider: Any,
    @SerializedName("providerId")
    val providerId: Any,
    @SerializedName("createDate")
    val createDate: String,
)