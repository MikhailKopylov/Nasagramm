package com.amk.nasagramm.ui

import androidx.fragment.app.Fragment
import com.amk.nasagramm.ui.dailyImage.DailyImageFragment
import com.amk.nasagramm.ui.settings.SettingsFragment
import com.amk.nasagramm.ui.marsPhoto.MarsPhotoContainerFragment

enum class FragmentType {

    EveryDayPhoto, Settings, MarsPhoto
}

object FactoryFragment {

    fun getInstance(fragmentType: FragmentType): Fragment {
        return when (fragmentType) {
            FragmentType.EveryDayPhoto -> DailyImageFragment()
            FragmentType.Settings -> SettingsFragment()
            FragmentType.MarsPhoto -> MarsPhotoContainerFragment()
        }
    }
}
