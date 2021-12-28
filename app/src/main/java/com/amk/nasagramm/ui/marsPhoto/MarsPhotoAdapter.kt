package com.amk.nasagramm.ui.marsPhoto

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amk.nasagramm.data.marsPhoto.RoversName

class MarsPhotoAdapter(
    fragment: MarsPhotoContainerFragment,
    val items: List<RoversName>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        val roversName = items[position]
        return MarsPhotoFragment.newInstance(roversName)
    }
}
