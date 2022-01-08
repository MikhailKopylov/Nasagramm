package ru.amk.mars_photo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import ru.amk.core.data.marsPhoto.RoversName
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.amk.mars_photo.R

class MarsPhotoContainerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_mars_photo_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.view_pager_mars_photo)
        val marsPhotoAdapter =
            MarsPhotoAdapter(this, RoversName.values().toList())
        viewPager.adapter = marsPhotoAdapter

        tabLayout = view.findViewById(R.id.tab_layout_rover_name)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val roverName = marsPhotoAdapter.items[position]
            tab.text = roverName.name
        }.attach()
    }
}
