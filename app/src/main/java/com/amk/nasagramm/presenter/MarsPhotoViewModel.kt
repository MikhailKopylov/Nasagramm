package com.amk.nasagramm.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.BuildConfig
import com.amk.nasagramm.data.NasaApiRetrofit
import com.amk.nasagramm.data.marsPhoto.MarsPhotoResponse
import com.amk.nasagramm.data.marsPhoto.RoversName
import com.amk.nasagramm.domain.DailyImage
import com.amk.nasagramm.domain.MarsPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsPhotoViewModel(
    private val liveDataForView: MutableLiveData<MarsPhoto> = MutableLiveData(),
    private val retrofitImpl: NasaApiRetrofit = NasaApiRetrofit(),
) : ViewModel() {

    private var roversName: RoversName = RoversName.Curiosity

    fun getData(roversName: RoversName): LiveData<MarsPhoto> {
        this.roversName = roversName
        sendServerRequest()
        return liveDataForView
    }

    private fun sendServerRequest() {
        liveDataForView.value = MarsPhoto.LoadingStopped

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("We need your api key"))
        } else {
            executeRequest(apiKey)
        }
    }

    private fun executeRequest(apiKey: String) {
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
        retrofitImpl.getNasaService().getMarsPhoto(roversName, apiKey).enqueue(callBack)
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
