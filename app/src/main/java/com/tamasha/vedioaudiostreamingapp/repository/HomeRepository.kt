package com.tamasha.vedioaudiostreamingapp.repository

import com.tamasha.vedioaudiostreamingapp.model.response.TokenResponse
import com.tamasha.vedioaudiostreamingapp.tokennetwork.ServiceBuilder
import okhttp3.Request
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    suspend fun fetchToken(request: Request): TokenResponse {
        return ServiceBuilder.fetchAuthToken(request)
    }
}