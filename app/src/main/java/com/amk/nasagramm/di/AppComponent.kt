package com.amk.nasagramm.di

import com.amk.nasagramm.MainActivity
import com.amk.nasagramm.ui.dailyImage.DailyImageFragment
import com.amk.nasagramm.ui.marsPhoto.MarsPhotoFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)
    fun injectDailyImageFragment(dailyImageFragment: DailyImageFragment)
    fun injectMarsPhotoFragment(marsPhotoFragment: MarsPhotoFragment)
}