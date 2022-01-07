package com.amk.nasagramm.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.BuildConfig
import com.amk.nasagramm.data.NasaApiService
import com.amk.nasagramm.domain.DailyImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DailyImageViewModel @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val liveDataForView: MutableLiveData<DailyImage>,
) : ViewModel() {

    fun getImageData(): LiveData<DailyImage> {
        sendServerRequest()
        return liveDataForView
    }

    fun updateData() {
        sendServerRequest()
    }

    private fun sendServerRequest() {
        liveDataForView.value = DailyImage.LoadingStopped

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("We need your api key"))
        } else {
            executeImageRequest(apiKey)
        }
    }

    private fun executeImageRequest(apiKey: String) {
        val callBack = object : Callback<com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse> {
            override fun onResponse(
                call: Call<com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse>,
                dailyImageResponse: Response<com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse>,
            ) {
                handleImageResponse(dailyImageResponse)
            }

            override fun onFailure(
                call: Call<com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse>,
                t: Throwable
            ) {
                liveDataForView.value = DailyImage.Error(t)
            }
        }
        nasaApiService.getEveryDayPhoto(apiKey).enqueue(callBack)
    }

    private fun handleImageResponse(dailyImageResponse: Response<com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse>) {
        if (dailyImageResponse.isSuccessful && dailyImageResponse.body() != null) {
            liveDataForView.value = DailyImage.Success(dailyImageResponse.body()!!)
            return
        }
        val message = dailyImageResponse.message()
        if (message.isNullOrEmpty()) {
            liveDataForView.value = DailyImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForView.value = DailyImage.Error(Throwable(message))
        }
    }
}
