package com.amk.nasagramm.domain

import com.amk.nasagramm.data.everyDayPhoto.DailyImageResponse

sealed class DailyImage {

    data class Success(val dailyImageResponse: DailyImageResponse) : DailyImage()

    data class Error(val error: Throwable) : DailyImage()

    data class LoadingInProgress(val progress: Int) : DailyImage()

    object LoadingStopped : DailyImage()
}
