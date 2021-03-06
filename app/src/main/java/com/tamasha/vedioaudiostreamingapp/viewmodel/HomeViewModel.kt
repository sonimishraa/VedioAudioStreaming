package com.tamasha.vedioaudiostreamingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamasha.vedioaudiostreamingapp.model.response.TokenResponse
import com.tamasha.vedioaudiostreamingapp.repository.HomeRepository
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Resource
import com.tamasha.vedioaudiostreamingapp.tokennetwork.ServiceBuilder
import com.tamasha.vedioaudiostreamingapp.util.crashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Request
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val repository = HomeRepository()
    val authTokenResponse = MutableLiveData<Resource<TokenResponse>>()

    fun fetchTokenByRoom(subdomain: String, roomId: String, role: String, env: String) {
        val request = ServiceBuilder.makeTokenWithRoomIdRequest(subdomain, roomId, role, env)
        sendAuthTokenRequest(request)
    }

    fun fetchTokenByCode(subdomain: String, code: String, env: String) {
        val request = ServiceBuilder.makeTokenWithCodeRequest(subdomain, code, env)
        sendAuthTokenRequest(request)
    }

    private fun sendAuthTokenRequest(request: Request) {
        viewModelScope.launch {
            val response = repository.fetchToken(request)
            authTokenResponse.postValue(Resource.loading())
            try {
                authTokenResponse.postValue(Resource.success(response))
            } catch (e: Exception) {
                crashlytics.recordException(e)
                authTokenResponse.postValue(Resource.error(e.message))
            }
        }

    }
}