package ru.amk.mars_photo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.api.load
import ru.amk.core.data.marsPhoto.MarsPhotoResponse
import ru.amk.core.data.marsPhoto.RoversName
import ru.amk.core.di.DaggerCoreComponent
import ru.amk.mars_photo.R
import ru.amk.mars_photo.di.DaggerMarsPhotoComponent
import ru.amk.mars_photo.domain.MarsPhoto
import ru.amk.mars_photo.presentation.MarsPhotoViewModel
import javax.inject.Inject

private const val ROVERS_NAME = "roversName"

class MarsPhotoFragment : Fragment() {

    @Inject
    lateinit var viewModel: MarsPhotoViewModel

    private lateinit var marsPhotoImageView: ImageView
    private lateinit var roverNameTextView: TextView
    private lateinit var marsDateTextView: TextView
    private lateinit var earthDateTextView: TextView
    private lateinit var caneraNameTextView: TextView
    private lateinit var fab: View
    private val roversName by lazy {
        requireArguments().getSerializable(ROVERS_NAME) as RoversName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerMarsPhotoComponent.builder()
            .coreComponent(DaggerCoreComponent.builder().build())
            .build()
        component.injectMarsPhotoFragment(this)
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
        marsDateTextView = view.findViewById(R.id.text_view_mars_date)
        earthDateTextView = view.findViewById(R.id.text_view_earth_date)
        caneraNameTextView = view.findViewById(R.id.text_view_camera_name)
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
                roverNameTextView.text = marsPhoto.marsPhotoResponse.photos[0].rover?.name ?: ""
                val dayOnMars = marsPhoto.marsPhotoResponse.photos[0].sol?.toString() ?: ""
                val dateOnEarth = marsPhoto.marsPhotoResponse.photos[0].earthDate ?: ""
                val cameraName = marsPhoto.marsPhotoResponse.photos[0].camera?.fullName ?: ""
                marsDateTextView.text = "$dayOnMars-й день на Марсе"
                earthDateTextView.text = "Дата на Земле: $dateOnEarth"
                caneraNameTextView.text = "Снято камерой: $cameraName"
                loadImage(response)
            }
            is MarsPhoto.LoadingInProgress -> {
            }//TODO()
            is MarsPhoto.Error -> {
            }//TODO()
            is MarsPhoto.LoadingStopped -> {
            }//TODO()
        }
    }

    private fun loadImage(response: MarsPhotoResponse) {
        val url = response.photos[0].imgSrc
        url?.let {
            if (it.isNotEmpty()) {
                val safetyUrl = checkUrl(it)
                showImage(safetyUrl)
            } else {
                marsPhotoImageView.load(R.drawable.ic_no_image)
            }
        } ?: marsPhotoImageView.load(R.drawable.ic_broken_image)
    }

    private fun checkUrl(url: String): String {
        url.elementAtOrNull(4)?.let {
            if (it != 's') {
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
