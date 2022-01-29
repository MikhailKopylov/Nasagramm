package com.amk.nasagramm.domain

import com.amk.nasagramm.data.marsPhoto.MarsPhotoResponse

sealed class MarsPhoto {

    data class Success(val marsPhotoResponse: MarsPhotoResponse) : MarsPhoto()

    data class Error(val error: Throwable) : MarsPhoto()

    data class LoadingInProgress(val progress: Int) : MarsPhoto()

    object LoadingStopped : MarsPhoto()
}
