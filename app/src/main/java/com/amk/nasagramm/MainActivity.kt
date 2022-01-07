package com.amk.nasagramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.amk.nasagramm.di.AppComponent
import com.amk.nasagramm.ui.FactoryFragment
import com.amk.nasagramm.ui.FragmentType
import com.amk.nasagramm.ui.dailyImage.DailyImageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var component: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (application as App).appComponent
        component.injectMainActivity(this)
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
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
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
