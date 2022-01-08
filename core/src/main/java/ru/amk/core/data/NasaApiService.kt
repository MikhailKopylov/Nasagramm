package ru.amk.core.data

import ru.amk.core.data.everyDayPhoto.DailyImageResponse
import ru.amk.core.data.marsPhoto.MarsPhotoResponse
import ru.amk.core.data.marsPhoto.RoversName
import ru.amk.core.data.marsPhoto.manifest.Manifest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    fun getEveryDayPhoto(@Query("api_key") apiKey: String): Call<DailyImageResponse>

    @GET("mars-photos/api/v1/rovers/{roversName}/photos?page=1")
    fun getMarsPhoto(
        @Path("roversName") roversName: RoversName,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotoResponse>

    @GET("mars-photos/api/v1/manifests/{roversName}")
    fun getMarsPhotoManifest(
        @Path("roversName") roversName: RoversName,
        @Query("api_key") apiKey: String,
    ): Call<Manifest>
}
