package com.example.SOOJOOB.retrofit

import com.google.gson.annotations.SerializedName

data class PloggingRequestBody(
    @SerializedName("distance")
    val distance: Double?,
    @SerializedName("timeRecord")
    val timeRecord: Int?,
    @SerializedName("trashCount")
    val trashCount: Int?,
    @SerializedName("ploggingImg")
    val ploggingImg: String?,
    @SerializedName("dateTime")
    val dateTime: String?

)
