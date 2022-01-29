package com.amk.nasagramm.data.marsPhoto

import com.google.gson.annotations.SerializedName

data class MarsPhotoResponse(
    @SerializedName("photos")
    val photos: List<Photo>,
)
