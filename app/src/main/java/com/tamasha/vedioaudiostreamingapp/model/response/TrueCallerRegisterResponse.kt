package com.tamasha.vedioaudiostreamingapp.model.response

import com.google.gson.annotations.SerializedName

data class TrueCallerRegisterResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
