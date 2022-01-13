package com.amk.nasagramm.core

sealed class ResponseResult {

    data class Success(val serviceEveryDayPhotoData: NasaEveryDayPhoto) : ResponseResult()
    data class Error(val error: Throwable) : ResponseResult()
    data class LoadingInProgress(val progress: Int) : ResponseResult()
    object LoadingStopped : ResponseResult()
}
