package com.amk.nasagramm.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.BuildConfig
import com.amk.nasagramm.core.NasaApiRetrofit
import com.amk.nasagramm.core.NasaEveryDayPhoto
import com.amk.nasagramm.core.ResponseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyImageViewModel(
    private val liveDataForView: MutableLiveData<ResponseResult> = MutableLiveData(),
    private val retrofitImpl: NasaApiRetrofit = NasaApiRetrofit(),
) : ViewModel() {

    fun getImageData(): LiveData<ResponseResult> {
        sendServerRequest()
        return liveDataForView
    }

    fun updateData() {
        sendServerRequest()
    }

    private fun sendServerRequest() {
        liveDataForView.value = ResponseResult.LoadingStopped

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            ResponseResult.Error(Throwable("We need your api key"))
        } else {
            executeImageRequest(apiKey)
        }
    }

    private fun executeImageRequest(apiKey: String) {
        val callBack = object : Callback<NasaEveryDayPhoto> {
            override fun onResponse(call: Call<NasaEveryDayPhoto>, everyDayPhoto: Response<NasaEveryDayPhoto>) {
                handleImageResponse(everyDayPhoto)
            }

            override fun onFailure(call: Call<NasaEveryDayPhoto>, t: Throwable) {
                liveDataForView.value = ResponseResult.Error(t)
            }
        }
        retrofitImpl.getNasaService().getImage(apiKey).enqueue(callBack)
    }

    private fun handleImageResponse(everyDayPhoto: Response<NasaEveryDayPhoto>) {
        if (everyDayPhoto.isSuccessful && everyDayPhoto.body() != null) {
            liveDataForView.value = ResponseResult.Success(everyDayPhoto.body()!!)
            return
        }
        val message = everyDayPhoto.message()
        if (message.isNullOrEmpty()) {
            liveDataForView.value = ResponseResult.Error(Throwable("Unidentified error"))
        } else {
            liveDataForView.value = ResponseResult.Error(Throwable(message))
        }
    }
}
