package com.amk.nasagramm.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.amk.core.di.AppProvider
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent : AppProvider {

    @Component.Builder
    interface AppComponentBuilder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): AppComponentBuilder
    }

    companion object {
        private var appProvider: AppProvider? = null
        fun createAppComponent(context: Context): AppProvider =
            appProvider
                ?: DaggerAppComponent
                    .builder()
                    .context(context)
                    .build()
    }
}