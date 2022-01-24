package com.amk.nasagramm.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Camera(

    @SerializedName("full_name")
    val fullNameCamera: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val nameCamera: String,

    @SerializedName("rover_id")
    val roverId: Int,
)
