package com.amk.nasagramm.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.amk.nasagramm.R
import com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse
import com.amk.nasagramm.domain.DailyImage
import com.amk.nasagramm.presenter.DailyImageViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

private const val ARG_PARAM1 = "param1"


class DailyImageFragment : Fragment() {

    private val viewModel by viewModels<DailyImageViewModel>()
    private lateinit var dailyImageView: ImageView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var isHdChip: Chip
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getImageData().observe(this, { dailyImage ->
            renderData(dailyImage)
        })
        arguments?.let { bundle ->
            param1 = bundle.getString(ARG_PARAM1)
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
            is DailyImage.LoadingInProgress -> {}//TODO()
            is DailyImage.Error -> {}//TODO()
            is DailyImage.LoadingStopped -> {}//TODO()
        }
    }

    private fun loadImage(url: String?, responseData: DailyImageResponse) {
        url?.let { url ->
            if (url.isNotEmpty()) {
                showImage(url)
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

