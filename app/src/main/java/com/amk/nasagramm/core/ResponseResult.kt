/*
 * Nasagramm
 * Copyright © 2021 AMK.
 */

package com.amk.nasagramm.core

sealed class ResponseResult {

    data class Success(val serviceResponseData: NasaResponse) : ResponseResult()
    data class Error(val error: Throwable) : ResponseResult()
    data class LoadingInProgress(val progress: Int) : ResponseResult()
    object LoadingStopped : ResponseResult()
}
