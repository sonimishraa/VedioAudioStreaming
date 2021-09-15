package com.tamasha.vedioaudiostreamingapp.model.response

import com.google.gson.annotations.SerializedName

data class SendOtpResponse(

	@field:SerializedName("response")
	val response: Any? = null,

	@field:SerializedName("error")
	val error: Error? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Error(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: Any? = null
)
