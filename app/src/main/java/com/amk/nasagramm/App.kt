package com.amk.nasagramm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.amk.nasagramm.ui.SettingManager
import com.amk.nasagramm.ui.Theme

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        when (SettingManager(this).getTheme()) {
            Theme.Day -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.Night -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.System -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}