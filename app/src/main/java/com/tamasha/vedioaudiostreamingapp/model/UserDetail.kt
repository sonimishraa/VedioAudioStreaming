package com.tamasha.vedioaudiostreamingapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    val number: String,
    val playerId: String,
    val deviceId: String
):Parcelable

