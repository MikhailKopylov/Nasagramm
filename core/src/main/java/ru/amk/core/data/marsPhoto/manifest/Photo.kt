package ru.amk.core.data.marsPhoto.manifest

import com.google.gson.annotations.SerializedName

data class Photo(
    val cameras: List<String>,
    @SerializedName("earth_date")
    val earthDate: String,
    val sol: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
)
