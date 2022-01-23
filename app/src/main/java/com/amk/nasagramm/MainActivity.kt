package com.amk.nasagramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.amk.dayli_image.ui.DailyImageFragment

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

        initBottomNavView()
    }

    private fun initBottomNavView() {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_app_bar)
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_home -> {
                    startFragment(FragmentType.EveryDayPhoto)
                    true
                }
                R.id.app_bar_mars_photo -> {
                    startFragment(FragmentType.MarsPhoto)
                    true
                }
                R.id.app_bar_settings -> {
                    startFragment(FragmentType.Settings)
                    true
                }
                else -> false
            }
        }

        bottomNavView.setOnItemReselectedListener {
            //TODO когда будет recyclerView реализовать переход наверх списка
        }
    }

    private fun startFragment(fragmentType: FragmentType) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                FactoryFragment.getInstance(fragmentType)
            )
            .commit()
    }
}
