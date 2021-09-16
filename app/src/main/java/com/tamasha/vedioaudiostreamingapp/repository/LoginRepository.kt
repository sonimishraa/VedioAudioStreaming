package com.tamasha.vedioaudiostreamingapp.repository

import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.SendOtpResponse
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun registerMobileRequest(request: NumberRegisterRequest):
            UserByPhoneResponse {
        return apiService.userLogin(request)
    }

    suspend fun sendOtpRequest(request: UserOtpRequest):
            SendOtpResponse {
        return apiService.senOtp(request)
    }

}