package com.amk.nasagramm

import androidx.fragment.app.Fragment
import ru.amk.dayli_image.ui.DailyImageFragment
import ru.amk.settings.ui.SettingsFragment

enum class FragmentType {

    EveryDayPhoto, Settings, MarsPhoto
}

object FactoryFragment {

    fun getInstance(fragmentType: FragmentType): Fragment {
        return when (fragmentType) {
            FragmentType.EveryDayPhoto -> DailyImageFragment()
            FragmentType.Settings -> SettingsFragment()
            FragmentType.MarsPhoto -> ru.amk.mars_photo.ui.MarsPhotoContainerFragment()
        }
    }
}
