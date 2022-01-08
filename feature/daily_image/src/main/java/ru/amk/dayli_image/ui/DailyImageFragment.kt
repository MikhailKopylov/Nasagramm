package ru.amk.dayli_image.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import coil.api.load
import ru.amk.core.data.everyDayPhoto.DailyImageResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.amk.core.di.DaggerCoreComponent
import ru.amk.dayli_image.R
import ru.amk.dayli_image.di.DaggerDailyImageComponent
import ru.amk.dayli_image.domain.DailyImage
import ru.amk.dayli_image.presentation.DailyImageViewModel
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class DailyImageFragment : Fragment() {

    @Inject
    lateinit var viewModel: DailyImageViewModel

    private lateinit var dailyImageView: ImageView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var isHdChip: Chip
    private var param1: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerDailyImageComponent.builder()
            .coreComponent(DaggerCoreComponent.builder().build())
            .build()
        component.injectDailyImageFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getImageData().observe(this, {
            renderData(it)
        })
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_image_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyImageView = view.findViewById(R.id.image_view_daily_image)
        descriptionTextView = view.findViewById(R.id.text_view_bottom_sheet_description)
        titleTextView = view.findViewById(R.id.text_view_bottom_sheet_description_header)
        isHdChip = view.findViewById(R.id.chip_select_hd)
        isHdChip.setOnClickListener {
            viewModel.updateData()
        }
        setBottomSheetBehavior(view.findViewById(R.id.description_bottom_sheet))
    }

    private fun renderData(dailyImage: DailyImage) {
        when (dailyImage) {
            is DailyImage.Success -> {
                val responseData = dailyImage.dailyImageResponse
                val url = if (isHdChip.isChecked) {
                    responseData.hdurl
                } else {
                    responseData.url
                }
                loadImage(url, responseData)
            }
            is DailyImage.LoadingInProgress -> {
            }//TODO()
            is DailyImage.Error -> {
            }//TODO()
            is DailyImage.LoadingStopped -> {
            }//TODO()
        }
    }

    private fun loadImage(url: String?, responseData: DailyImageResponse) {
        url?.let {
            if (it.isNotEmpty()) {
                showImage(it)
            } else {
                dailyImageView.load(R.drawable.ic_no_image)
            }
        } ?: dailyImageView.load(R.drawable.ic_broken_image)
        responseData.explanation?.let { descriptionTextView.text = it }
        responseData.title?.let { titleTextView.text = it }
    }

    private fun showImage(url: String) {
        dailyImageView.load(url) {
            lifecycle(this@DailyImageFragment)
            error(R.drawable.ic_broken_image)
            placeholder(R.drawable.ic_no_image)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}

