package com.amk.nasagramm.data.marsPhoto.manifest

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("cameras")
    val cameras: List<String>,
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("sol")
    val dayOnMars: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
)
