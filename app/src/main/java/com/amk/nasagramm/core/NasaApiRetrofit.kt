package com.amk.nasagramm.core

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaApiRetrofit {

    private val baseUrl = "https://api.nasa.gov/"

    fun getNasaService(): NasaApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(NasaApiService::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(logger)
        return client.build()
    }

}
