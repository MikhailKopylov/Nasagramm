package com.amk.nasagramm.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Photo(

    @SerializedName("camera")
    val camera: Camera?,

    @SerializedName("earth_date")
    val earthDate: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("img_src")
    internal val imgSrc: String?,

    @SerializedName("rover")
    val roversName: Rover?,

    @SerializedName("sol")
    val dayOnMars: Int?,
)
