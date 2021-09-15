package com.tamasha.vedioaudiostreamingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.SendOtpResponse
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.repository.LoginRepository
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Resource
import com.tamasha.vedioaudiostreamingapp.util.crashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    val userMobileRegisterResponse = MutableLiveData<Resource<UserByPhoneResponse>>()
    val userOtpResponse = MutableLiveData<Resource<SendOtpResponse>>()

    fun registerNumberRequest(request: NumberRegisterRequest) {
        viewModelScope.launch {
            val response = repository.registerMobileRequest(request)
            userMobileRegisterResponse.postValue(Resource.loading())
            try {
                userMobileRegisterResponse.postValue(Resource.success(response))
            } catch (e: Exception) {
                crashlytics.recordException(e)
                userMobileRegisterResponse.postValue(Resource.error(e.message))
            }
        }

    }

    fun sendOtpRequest(request: UserOtpRequest) {
        viewModelScope.launch {
            val response = repository.sendOtpRequest(request)
            userOtpResponse.postValue(Resource.loading())
            try {
                userOtpResponse.postValue(Resource.success(response))
            } catch (e: Exception) {
                crashlytics.recordException(e)
                userOtpResponse.postValue(Resource.error(e.message))
            }
        }

    }
}

