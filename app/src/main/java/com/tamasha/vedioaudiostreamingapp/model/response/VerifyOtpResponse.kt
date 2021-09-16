package com.tamasha.vedioaudiostreamingapp.model.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(

	@field:SerializedName("new_user")
	val newUser: Boolean? = null,

	@field:SerializedName("wallet_id")
	val walletId: Int? = null,

	@field:SerializedName("player_id")
	val playerId: String? = null,

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("signUpBonusMsg")
	val signUpBonusMsg: String? = null,

	@field:SerializedName("error")
	val error: Error? = null,

	@field:SerializedName("auth_token")
	val authToken: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Response(

	@field:SerializedName("UserId")
	val userId: String? = null
)

/*
data class Error(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: Any? = null
)
*/
