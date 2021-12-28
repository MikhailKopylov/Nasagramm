package com.amk.nasagramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.amk.nasagramm.ui.DailyImageFragment
import com.amk.nasagramm.ui.FactoryFragment
import com.amk.nasagramm.ui.FragmentType
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottom_app_bar)
    }

    private val navigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        val fragment = when (item.itemId) {
            R.id.app_bar_home -> FactoryFragment.getInstance(FragmentType.EveryDayPhoto)
            R.id.app_bar_settings -> FactoryFragment.getInstance(FragmentType.Settings)
            R.id.app_bar_fav -> FactoryFragment.getInstance(FragmentType.MarsPhoto)
            else -> throw IllegalArgumentException("select unknown item")
        }
        openScreen(fragment)
        true
    }

    private val navigationItemReselectedListener = NavigationBarView.OnItemReselectedListener {
        // not used
    }

    private fun openScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, DailyImageFragment())
            }
        }
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener)
        bottomNavigationView.setOnItemReselectedListener(navigationItemReselectedListener)
        initSharedPreferences()
    }

    private fun initSharedPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString("themes", "themeSystem")) {
            "themeLight" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "themeNight" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "themeSystem" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
