package com.amk.nasagramm

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val switchKey: ListPreference? = findPreference("themes")
        switchKey?.setOnPreferenceChangeListener { preference, newValue ->
            if ((preference as ListPreference).value != newValue) {
                Toast.makeText(context, "Для смены темы перезапустите приложение", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}
