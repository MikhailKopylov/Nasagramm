package ru.amk.core.data.marsPhoto.manifest

import com.google.gson.annotations.SerializedName

data class Manifest(
    @SerializedName("photo_manifest")
    val photoManifest: PhotoManifest,
)
