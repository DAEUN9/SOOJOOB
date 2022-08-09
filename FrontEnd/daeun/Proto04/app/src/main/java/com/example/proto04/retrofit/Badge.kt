package com.example.proto04.retrofit

import java.io.Serializable

data class Badge(
    var id: Integer?,
    var badgeName: String?,
    var badgeDetail: String?,
    var imgUrl: String?
):Serializable{}
