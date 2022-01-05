package com.amk.nasagramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.amk.nasagramm.ui.DailyImageFragment
import com.amk.nasagramm.ui.FactoryFragment
import com.amk.nasagramm.ui.FragmentType
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

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_app_bar)
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
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
