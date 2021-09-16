package com.tamasha.vedioaudiostreamingapp.database.dao

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "encrypted")
@JvmSuppressWildcards
data class Encrypted(
    @PrimaryKey
    var index: String,
    var category: String?,
    var value: String,
    var iv: String
)
