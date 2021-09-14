package com.tamasha.vedioaudiostreamingapp.repository

import com.tamasha.vedioaudiostreamingapp.model.TokenResponse
import com.tamasha.vedioaudiostreamingapp.tokennetwork.ServiceBuilder
import okhttp3.Request

class HomeRepository {

    suspend fun fetchToken(request: Request): TokenResponse {
        return ServiceBuilder.fetchAuthToken(request)
    }
}