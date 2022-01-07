package com.amk.nasagramm

import android.app.Application
import com.amk.nasagramm.di.AppComponent
import com.amk.nasagramm.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}