package com.tamasha.vedioaudiostreamingapp.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomDetails(
    val env: String,
    val url: String,
    val username: String,
    val authToken: String
) : Parcelable
