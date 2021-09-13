package com.iotric.vedioaudiostreamingapp.repository

import com.iotric.vedioaudiostreamingapp.model.TokenResponse
import com.iotric.vedioaudiostreamingapp.network.ServiceBuilder
import okhttp3.Request

class HomeRepository {

    suspend fun fetchToken(request:Request):TokenResponse {
        return ServiceBuilder.fetchAuthToken(request)
    }
}