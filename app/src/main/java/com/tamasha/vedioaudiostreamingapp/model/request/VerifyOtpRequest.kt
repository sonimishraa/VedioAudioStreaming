package com.tamasha.vedioaudiostreamingapp.model.request

data class VerifyOtpRequest(
    val ClientOTP:String,
    val MobileNumber: String,
    val UserId: String,
    val DeviceID: String
)
