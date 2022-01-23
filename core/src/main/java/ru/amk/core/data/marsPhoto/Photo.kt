package ru.amk.core.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Photo(
    val camera: Camera?,
    @SerializedName("earth_date")
    val earthDate: String?,
    val id: Int?,
    @SerializedName("img_src")
    internal val imgSrc: String?,
    val rover: Rover?,
    val sol: Int?
)
