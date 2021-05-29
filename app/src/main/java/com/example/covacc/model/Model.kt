package com.example.covacc.model

data class Model(
    val centerName: String,
    val centerAddress: String,
    val centerFromTime: String,
    val centerToTime: String,
    val fees_Type: String,
    val ageLimit: Int,
    var vaccinName: String,
    val availableCapacity: Int

)