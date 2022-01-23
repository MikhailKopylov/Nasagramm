package ru.amk.core.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class Camera(
    @SerializedName("full_name")
    val fullName: String,
    val id: Int,
    val name: String,
    @SerializedName("rover_id")
    val roverId: Int
)
