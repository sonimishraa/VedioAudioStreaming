package com.tamasha.vedioaudiostreamingapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.repository.LoginRepository
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Resource
import com.tamasha.vedioaudiostreamingapp.util.crashlytics
import kotlinx.coroutines.launch
import retrofit2.Call

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    val userLoginResponse = MutableLiveData<Resource<Call<UserByPhoneResponse>>>()

    fun sendOtpRequest(request: UserOtpRequest, application: Application) {
        viewModelScope.launch {
            val response = repository.sendOtpRequest(request, application)
            userLoginResponse.postValue(Resource.loading())
            try {
                userLoginResponse.postValue(Resource.success(response))
            } catch (e: Exception) {
                crashlytics.recordException(e)
                userLoginResponse.postValue(Resource.error(e.message))
            }
        }

    }
}

