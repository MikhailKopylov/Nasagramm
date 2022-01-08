package ru.amk.core.di

import dagger.Component
import ru.amk.core.data.NasaApiService

@Component(modules = [RetrofitModule::class])
interface CoreComponent {

    fun getNasaApiService(): NasaApiService
}