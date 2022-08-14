package com.example.SOOJOOB.retrofit

import java.io.Serializable

data class Badge(
    var id: Long?,
    var badgeName: String?,
    var badgeDetail: String?,
    var imgUrl: String?
):Serializable{}
