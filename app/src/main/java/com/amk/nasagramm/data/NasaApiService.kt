package com.amk.nasagramm.data

import com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse
import com.amk.nasagramm.data.marsPhoto.MarsPhotoResponse
import com.amk.nasagramm.data.marsPhoto.RoversName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    fun getEveryDayPhoto(@Query("api_key") apiKey: String): Call<DailyImageResponse>

    @GET("mars-photos/api/v1/rovers/{roversName}/photos?sol=1&page=1")
    fun getMarsPhoto(
        @Path("roversName") roversName: RoversName,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotoResponse>
}
