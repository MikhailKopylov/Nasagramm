package com.amk.nasagramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import com.amk.nasagramm.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, DailyImageFragment())
            }
        }

        when (SettingManager(this).getTheme()) {
            Theme.Day -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.Night -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.System -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_app_bar)
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FactoryFragment.getInstance(FragmentType.EveryDayPhoto))
                        .commit()
                    true
                }
                R.id.app_bar_fav -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FactoryFragment.getInstance(FragmentType.MarsPhoto))
                        .commit()
                    true
                }
                R.id.app_bar_settings -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FactoryFragment.getInstance(FragmentType.Settings))
                        .commit()
                    true
                }
                else -> false
            }
        }
        bottomNavView.setOnItemReselectedListener {
            //TODO когда будет recyclerView реализовать переход наверх списка
        }
    }
}
