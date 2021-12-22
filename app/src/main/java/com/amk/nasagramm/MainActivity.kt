/*
 * Nasagramm
 * Copyright © 2021 AMK.
 */

package com.amk.nasagramm


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.amk.nasagramm.ui.DailyImageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, DailyImageFragment.newInstance())
            }
        }
        setBottomAppBar()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString("themes", "themeSystem")) {
            "themeLight" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "themeNight" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "themeSystem" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_bottom_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(this, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment())
                    .commit()
            }
            R.id.app_bar_home -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, DailyImageFragment())
                    .commit()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar() {
        this.setSupportActionBar(findViewById(R.id.bottom_app_bar))
    }
}
