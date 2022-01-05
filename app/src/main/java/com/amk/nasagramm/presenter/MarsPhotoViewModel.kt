package com.amk.nasagramm.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.BuildConfig
import com.amk.nasagramm.data.NasaApiRetrofit
import com.amk.nasagramm.data.marsPhoto.MarsPhotoResponse
import com.amk.nasagramm.data.marsPhoto.RoversName
import com.amk.nasagramm.data.marsPhoto.manifest.Manifest
import com.amk.nasagramm.data.marsPhoto.manifest.Photo
import com.amk.nasagramm.domain.DailyImage
import com.amk.nasagramm.domain.MarsPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val DEFAULT_DAY_ON_MARS = 1

class MarsPhotoViewModel(
    private val liveDataForView: MutableLiveData<MarsPhoto> = MutableLiveData(),
    private val retrofitImpl: NasaApiRetrofit = NasaApiRetrofit(),
) : ViewModel() {

    private var roversName: RoversName = RoversName.Curiosity
    private var photoList = mutableListOf<Photo>()
    private var dayOnMars = DEFAULT_DAY_ON_MARS
    private lateinit var apiKey: String

    fun getData(roversName: RoversName): LiveData<MarsPhoto> {
        this.roversName = roversName
        sendServerRequest()
        return liveDataForView
    }

    fun randomDate() {
        dayOnMars = photoList.randomOrNull()?.sol ?: DEFAULT_DAY_ON_MARS
        executeRequest()
    }

    private fun sendServerRequest() {
        liveDataForView.value = MarsPhoto.LoadingStopped

        apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("We need your api key"))
        } else {
            initMaxDayOnMars()
        }
    }

    private fun initMaxDayOnMars() {
        val callback = object : Callback<Manifest> {
            override fun onResponse(call: Call<Manifest>, response: Response<Manifest>) {
                if (response.isSuccessful && response.body() != null) {
                    photoList.addAll(response.body()?.photo_manifest?.photos ?: listOf())
                    dayOnMars = photoList.randomOrNull()?.sol ?: DEFAULT_DAY_ON_MARS
                    executeRequest()
                }
            }

            override fun onFailure(call: Call<Manifest>, t: Throwable) {
                executeRequest()
            }
        }
        retrofitImpl.getNasaService().getMarsPhotoManifest(roversName, apiKey).enqueue(callback)
    }

    private fun executeRequest() {
        val callBack = object : Callback<MarsPhotoResponse> {
            override fun onResponse(
                call: Call<MarsPhotoResponse>,
                marsPhotoResponse: Response<MarsPhotoResponse>,
            ) {
                handleImageResponse(marsPhotoResponse)
            }

            override fun onFailure(call: Call<MarsPhotoResponse>, t: Throwable) {
                liveDataForView.value = MarsPhoto.Error(t)
            }
        }
        retrofitImpl.getNasaService().getMarsPhoto(roversName, dayOnMars, apiKey).enqueue(callBack)
    }

    private fun handleImageResponse(marsPhotoResponse: Response<MarsPhotoResponse>) {
        if (marsPhotoResponse.isSuccessful && marsPhotoResponse.body() != null) {
            liveDataForView.value = MarsPhoto.Success(marsPhotoResponse.body()!!)
            return
        }
        val message = marsPhotoResponse.message()
        if (message.isNullOrEmpty()) {
            liveDataForView.value = MarsPhoto.Error(Throwable("Unidentified error"))
        } else {
            liveDataForView.value = MarsPhoto.Error(Throwable(message))
        }
    }
}
