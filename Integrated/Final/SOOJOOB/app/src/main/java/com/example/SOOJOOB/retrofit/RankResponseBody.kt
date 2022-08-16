package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RankResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: List<RankData>?
)

data class RankData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("userRecord")
    val userRecord: RecordInfo?,
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
):Serializable{}

data class RecordInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
):Serializable{}

