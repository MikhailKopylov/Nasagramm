package ru.amk.core.data.marsPhoto.manifest

import com.google.gson.annotations.SerializedName

data class PhotoManifest(

    @SerializedName("launch_date")
    val launchDate: String,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("max_date")
    val maxDate: String,
    @SerializedName("max_sol")
    val maxSol: Int,
    val name: String,
    val photos: List<Photo>,
    val status: String,
    @SerializedName("total_photos")
    val totalPhotos: Int,
)
