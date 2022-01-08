package ru.amk.mars_photo.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.amk.core.data.marsPhoto.RoversName

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
