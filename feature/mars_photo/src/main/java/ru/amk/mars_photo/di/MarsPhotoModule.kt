package ru.amk.mars_photo.di

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.amk.core.data.NasaApiService
import ru.amk.core.di.ViewModelKey
import ru.amk.mars_photo.domain.MarsPhoto
import ru.amk.mars_photo.presentation.MarsPhotoViewModel

@Module
class MarsPhotoModule {

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
    fun provideLiveDataForMarsPhoto(): MutableLiveData<MarsPhoto> = MutableLiveData()
}