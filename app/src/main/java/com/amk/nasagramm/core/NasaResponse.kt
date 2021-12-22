/*
 * Nasagramm
 * Copyright © 2021 AMK.
 */

package com.amk.nasagramm.core

data class NasaResponse(
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?,
)
