package com.amk.nasagramm.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

private const val STORAGE = "storage"
private const val THEME = "theme"

class SettingManager(
    context: Context,
) {

    private val preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)

    fun saveTheme(theme: Theme) {
        if (getTheme() != theme) {
            preferences.edit()
                .putInt(THEME, theme.ordinal)
                .apply()

            when (theme) {
                Theme.Day -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Theme.Night -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Theme.System -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    fun getTheme(): Theme = Theme.values()[preferences.getInt(THEME, Theme.System.ordinal)]
}
