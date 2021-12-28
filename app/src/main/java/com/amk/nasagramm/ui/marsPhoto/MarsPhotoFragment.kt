package com.amk.nasagramm.ui.marsPhoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.amk.nasagramm.R
import com.amk.nasagramm.data.marsPhoto.MarsPhotoResponse
import com.amk.nasagramm.data.marsPhoto.RoversName
import com.amk.nasagramm.domain.MarsPhoto
import com.amk.nasagramm.presenter.MarsPhotoViewModel

private const val ROVERS_NAME = "roversName"

class MarsPhotoFragment : Fragment() {

    private val viewModel by viewModels<MarsPhotoViewModel>()
    private lateinit var marsPhotoImageView: ImageView
    private lateinit var roverNameTextView: TextView
    private val roversName by lazy {
        requireArguments().getSerializable(ROVERS_NAME) as RoversName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(roversName).observe(this, {
            renderData(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_mars_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marsPhotoImageView = view.findViewById(R.id.image_view_mars_photo)
        roverNameTextView = view.findViewById(R.id.text_view_rover_name)
    }

    private fun renderData(marsPhoto: MarsPhoto) {
        when (marsPhoto) {
            is MarsPhoto.Success -> {
                val response = marsPhoto.marsPhotoResponse
                roverNameTextView.text = marsPhoto.marsPhotoResponse.photos[0].rover?.name ?: ""
                loadImage(response)
            }
            is MarsPhoto.LoadingInProgress -> {}//TODO()
            is MarsPhoto.Error -> {}//TODO()
            is MarsPhoto.LoadingStopped -> {}//TODO()
        }
    }

    private fun loadImage(response: MarsPhotoResponse) {
        val url = response.photos[0].img_src
        url?.let {
            if (it.isNotEmpty()) {
                showImage(it)
            } else {
                marsPhotoImageView.load(R.drawable.ic_no_image)
            }
        } ?: marsPhotoImageView.load(R.drawable.ic_broken_image)
    }

    private fun showImage(url: String) {
        marsPhotoImageView.load(url) {
            lifecycle(this@MarsPhotoFragment)
            error(R.drawable.ic_broken_image)
            placeholder(R.drawable.ic_no_image)
        }
    }

    companion object {

        fun newInstance(roversName: RoversName): MarsPhotoFragment {
            val bundle = Bundle()
            bundle.putSerializable(ROVERS_NAME, roversName)

            val fragment = MarsPhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
