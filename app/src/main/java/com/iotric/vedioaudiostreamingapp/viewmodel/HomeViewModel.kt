package com.iotric.vedioaudiostreamingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iotric.vedioaudiostreamingapp.model.TokenResponse
import com.iotric.vedioaudiostreamingapp.network.Resource
import com.iotric.vedioaudiostreamingapp.network.ServiceBuilder
import com.iotric.vedioaudiostreamingapp.repository.HomeRepository
import com.iotric.vedioaudiostreamingapp.util.crashlytics
import kotlinx.coroutines.launch
import okhttp3.Request

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()
    val authTokenResponse = MutableLiveData<Resource<TokenResponse>>()

    fun fetchToken(subdomain: String, roomId: String, role: String, env: String) {
        val request = ServiceBuilder.makeTokenWithRoomIdRequest(subdomain,roomId,role,env)
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