package com.amk.nasagramm.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate

const val STORAGE = "storage"
const val THEME = "theme"

class SettingManager(
    private val context: Context,
) {

    private val pref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)

    fun saveTheme(theme: Theme) {
        if (getTheme() != theme) {
            pref.edit()
                .putInt(THEME, theme.ordinal)
                .apply()

            when(theme){
               Theme.Day -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
               Theme.Night -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               Theme.System -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            restartApp()
        }
    }

    fun getTheme(): Theme = Theme.values()[pref.getInt(THEME, Theme.System.ordinal)]

    private fun restartApp() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)

        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            (context as Activity).finish()
            context.startActivity(it)
        }
    }
}
