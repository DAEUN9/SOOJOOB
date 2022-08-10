package com.example.proto04.retrofit

import java.io.Serializable

data class Plogging (
    var id: Integer?,
    var ploggingUser: Integer?,
    var distance: Double?,
    var timeRecord: Double?,
    var dateTime: Double?,
    var stepCount: Integer?,
    var trashCount: Integer?,
    var ploggingImg: String?
):Serializable{}
