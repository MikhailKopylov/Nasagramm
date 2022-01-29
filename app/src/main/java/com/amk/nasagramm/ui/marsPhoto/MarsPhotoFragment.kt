package com.amk.nasagramm.ui.marsPhoto

import android.annotation.SuppressLint
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
    private lateinit var marsDateTextView: TextView
    private lateinit var earthDateTextView: TextView
    private lateinit var cameraNameTextView: TextView
    private lateinit var fab: View
    private val roversName by lazy {
        requireArguments().getSerializable(ROVERS_NAME) as RoversName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(roversName).observe(this, { marsPhoto ->
            renderData(marsPhoto)
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
        marsDateTextView = view.findViewById(R.id.text_view_mars_date)
        earthDateTextView = view.findViewById(R.id.text_view_earth_date)
        cameraNameTextView = view.findViewById(R.id.text_view_camera_name)
        fab = view.findViewById(R.id.fab_random_date)
        fab.setOnClickListener {
            viewModel.randomDate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(marsPhoto: MarsPhoto) {
        when (marsPhoto) {
            is MarsPhoto.Success -> {
                val response = marsPhoto.marsPhotoResponse
                roverNameTextView.text = marsPhoto.marsPhotoResponse.photos[0].roversName?.roverName ?: ""
                val dayOnMars = marsPhoto.marsPhotoResponse.photos[0].dayOnMars?.toString() ?: ""
                val dateOnEarth = marsPhoto.marsPhotoResponse.photos[0].earthDate ?: ""
                val cameraName = marsPhoto.marsPhotoResponse.photos[0].camera?.fullNameCamera ?: ""
                marsDateTextView.text = "$dayOnMars-й день на Марсе"
                earthDateTextView.text = "Дата на Земле: $dateOnEarth"
                cameraNameTextView.text = "Снято камерой: $cameraName"
                loadImage(response)
            }
            is MarsPhoto.LoadingInProgress -> {}//TODO()
            is MarsPhoto.Error -> {}//TODO()
            is MarsPhoto.LoadingStopped -> {}//TODO()
        }
    }

    private fun loadImage(response: MarsPhotoResponse) {
        val url = response.photos[0].imgSrc
        url?.let { url ->
            if (url.isNotEmpty()) {
                val safetyUrl = checkUrl(url)
                showImage(safetyUrl)
            } else {
                marsPhotoImageView.load(R.drawable.ic_no_image)
            }
        } ?: marsPhotoImageView.load(R.drawable.ic_broken_image)
    }

    private fun checkUrl(url: String): String {
        url.elementAtOrNull(4)?.let { char ->
            if (char != 's') {
                return StringBuilder(url).apply { insert(4, 's') }.toString()
            }
        }
        return url
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
