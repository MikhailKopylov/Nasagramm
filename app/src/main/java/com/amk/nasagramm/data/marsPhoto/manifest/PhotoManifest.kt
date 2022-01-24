package com.amk.nasagramm.data.marsPhoto.manifest

import com.google.gson.annotations.SerializedName

data class PhotoManifest(

    @SerializedName("launch_date")
    val launchDate: String,

    @SerializedName("landing_date")
    val landingDate: String,

    @SerializedName("max_date")
    val maxDateEarth: String,

    @SerializedName("max_sol")
    val maxDayOnMars: Int,

    @SerializedName("name")
    val roversName: String,

    @SerializedName("photos")
    val photos: List<Photo>,

    @SerializedName("status")
    val status: String,

    @SerializedName("total_photos")
    val totalPhotos: Int,
)
