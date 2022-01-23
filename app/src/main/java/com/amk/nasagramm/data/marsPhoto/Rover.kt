package com.amk.nasagramm.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Rover(
    val id: Int,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchDate: String,
    val name: String,
    val status: String
)
