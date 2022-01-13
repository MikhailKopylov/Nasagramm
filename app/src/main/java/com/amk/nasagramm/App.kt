package com.amk.nasagramm

import android.app.Application
import com.amk.nasagramm.di.AppComponent
import ru.amk.core.di.AppProvider
import ru.amk.core.di.AppWithFacade

class App : Application(), AppWithFacade {
    companion object {
        lateinit var appProvider: AppProvider
    }

    override fun onCreate() {
        super.onCreate()
        appProvider = AppComponent.createAppComponent(this)
    }

    override fun getAppProvider(): AppProvider = appProvider
}
