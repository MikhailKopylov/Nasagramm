package ru.amk.dayli_image.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.amk.core.data.NasaApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.amk.core.BuildConfig
import ru.amk.core.data.ApiKey
import ru.amk.core.data.everyDayPhoto.DailyImageResponse
import ru.amk.dayli_image.domain.DailyImage
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
        val callBack = object : Callback<DailyImageResponse> {
            override fun onResponse(
                call: Call<DailyImageResponse>,
                dailyImageResponse: Response<DailyImageResponse>,
            ) {
                handleImageResponse(dailyImageResponse)
            }

            override fun onFailure(call: Call<DailyImageResponse>, t: Throwable) {
                liveDataForView.value = DailyImage.Error(t)
            }
        }
        nasaApiService.getEveryDayPhoto(apiKey).enqueue(callBack)
    }

    private fun handleImageResponse(dailyImageResponse: Response<DailyImageResponse>) {
        if (dailyImageResponse.isSuccessful && dailyImageResponse.body() != null) {
            liveDataForView.value =
                DailyImage.Success(dailyImageResponse.body()!!)
            return
        }
        val message = dailyImageResponse.message()
        if (message.isNullOrEmpty()) {
            liveDataForView.value =
                DailyImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForView.value = DailyImage.Error(Throwable(message))
        }
    }
}
