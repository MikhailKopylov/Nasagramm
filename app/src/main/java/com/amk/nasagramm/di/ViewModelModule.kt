package com.amk.nasagramm.di

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.nasagramm.data.NasaApiService
import com.amk.nasagramm.domain.DailyImage
import com.amk.nasagramm.domain.MarsPhoto
import com.amk.nasagramm.presenter.DailyImageViewModel
import com.amk.nasagramm.presenter.MarsPhotoViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(DailyImageViewModel::class)
    @Provides
    fun provideDailyImageViewModel(
        nasaApiService: NasaApiService,
        liveData: MutableLiveData<DailyImage>
    ): ViewModel {
        return DailyImageViewModel(nasaApiService, liveData)
    }

    @IntoMap
    @ViewModelKey(MarsPhotoViewModel::class)
    @Provides
    fun provideMarsPhotoViewModel(
        nasaApiService: NasaApiService,
        liveData: MutableLiveData<MarsPhoto>
    ): ViewModel {
        return MarsPhotoViewModel(nasaApiService, liveData)
    }

    @Provides
    fun provideLiveDataForDailyImage(): MutableLiveData<DailyImage> = MutableLiveData()

    @Provides
    fun provideLiveDataForMarsPhoto(): MutableLiveData<MarsPhoto> = MutableLiveData()
}