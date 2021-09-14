package com.tamasha.vedioaudiostreamingapp.model

import com.google.gson.annotations.SerializedName

data class UserByPhoneResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("walletId")
	val walletId: Int? = null,

	@field:SerializedName("alreadyLoggedIn")
	val alreadyLoggedIn: Boolean? = null,

	@field:SerializedName("playerId")
	val playerId: String? = null
)
