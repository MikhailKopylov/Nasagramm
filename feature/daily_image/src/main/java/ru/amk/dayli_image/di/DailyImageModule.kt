package ru.amk.dayli_image.di

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.amk.core.di.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.amk.core.data.NasaApiService
import ru.amk.dayli_image.domain.DailyImage
import ru.amk.dayli_image.presentation.DailyImageViewModel

@Module
class DailyImageModule {
    @IntoMap
    @ViewModelKey(DailyImageViewModel::class)
    @Provides
    fun provideDailyImageViewModel(
        nasaApiService: NasaApiService,
        liveData: MutableLiveData<DailyImage>
    ): ViewModel {
        return DailyImageViewModel(nasaApiService, liveData)
    }

    @Provides
    fun provideLiveDataForDailyImage(): MutableLiveData<DailyImage> = MutableLiveData()

}