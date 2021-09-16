package com.tamasha.vedioaudiostreamingapp.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserByPhoneResponse(

    @field:SerializedName("data")
	val data: Data,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("walletId")
	val walletId: Int,

	@field:SerializedName("alreadyLoggedIn")
	val alreadyLoggedIn: Boolean,

	@field:SerializedName("playerId")
	val playerId: String
)
