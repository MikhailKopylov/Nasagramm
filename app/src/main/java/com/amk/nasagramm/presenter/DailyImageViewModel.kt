package com.amk.nasagramm.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.BuildConfig
import com.amk.nasagramm.core.NasaApiRetrofit
import com.amk.nasagramm.core.NasaResponse
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
        val callBack = object : Callback<NasaResponse> {
            override fun onResponse(call: Call<NasaResponse>, response: Response<NasaResponse>) {
                handleImageResponse(response)
            }

            override fun onFailure(call: Call<NasaResponse>, t: Throwable) {
                liveDataForView.value = ResponseResult.Error(t)
            }
        }
        retrofitImpl.getNasaService().getImage(apiKey).enqueue(callBack)
    }

    private fun handleImageResponse(response: Response<NasaResponse>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForView.value = ResponseResult.Success(response.body()!!)
            return
        }
        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForView.value = ResponseResult.Error(Throwable("Unidentified error"))
        } else {
            liveDataForView.value = ResponseResult.Error(Throwable(message))
        }

    }
}
