package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName

data class PloggingGetResponseBody(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val result: List<PloggingResult>?
)

data class PloggingResult(
    @SerializedName("distance")
    val distance : Double?,
    @SerializedName("timeRecord")
    val timeRecord: Int?,
    @SerializedName("trashCount")
    val trashCount: Int?,
    @SerializedName("ploggingImg")
    val ploggingImg: Any?,
    @SerializedName("dateTime")
    val dateTime: String?
)

