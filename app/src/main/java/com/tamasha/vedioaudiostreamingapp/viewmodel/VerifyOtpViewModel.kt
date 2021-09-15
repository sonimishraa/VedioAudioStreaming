package com.tamasha.vedioaudiostreamingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.request.VerifyOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.SendOtpResponse
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.model.response.VerifyOtpResponse
import com.tamasha.vedioaudiostreamingapp.repository.LoginRepository
import com.tamasha.vedioaudiostreamingapp.repository.VerifyOtpRepository
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Resource
import com.tamasha.vedioaudiostreamingapp.util.crashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(private val repository: VerifyOtpRepository) : ViewModel() {
    val verifyOtpResponse = MutableLiveData<Resource<VerifyOtpResponse>>()

    fun verifyOtpRequest(request: VerifyOtpRequest) {
        viewModelScope.launch {
            val response = repository.verifyOtpRequest(request)
            verifyOtpResponse.postValue(Resource.loading())
            try {
                verifyOtpResponse.postValue(Resource.success(response))
            } catch (e: Exception) {
                crashlytics.recordException(e)
                verifyOtpResponse.postValue(Resource.error(e.message))
            }
        }

    }

}

