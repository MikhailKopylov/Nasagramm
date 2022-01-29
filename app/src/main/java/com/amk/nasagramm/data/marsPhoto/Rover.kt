package com.amk.nasagramm.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    val id: Int,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchDate: String,
    @SerializedName("name")
    val roverName: String,
    @SerializedName("status")
    val status: String,
)
