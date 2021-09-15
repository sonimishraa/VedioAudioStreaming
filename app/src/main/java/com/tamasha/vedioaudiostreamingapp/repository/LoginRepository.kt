package com.tamasha.vedioaudiostreamingapp.repository

import android.app.Application
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.network.RetrofitBuilder
import retrofit2.Call

class LoginRepository {

    suspend fun sendOtpRequest(request: UserOtpRequest, application: Application):
            Call<UserByPhoneResponse> {
        return RetrofitBuilder.getRetrofit(application).userLogin(request)
    }

}