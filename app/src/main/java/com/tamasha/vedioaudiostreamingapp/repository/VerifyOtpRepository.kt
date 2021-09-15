package com.tamasha.vedioaudiostreamingapp.repository

import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.request.VerifyOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.SendOtpResponse
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.model.response.VerifyOtpResponse
import com.tamasha.vedioaudiostreamingapp.network.ApiService
import javax.inject.Inject

class VerifyOtpRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun verifyOtpRequest(request: VerifyOtpRequest):
            VerifyOtpResponse {
        return apiService.verifyOtp(request)
    }


}