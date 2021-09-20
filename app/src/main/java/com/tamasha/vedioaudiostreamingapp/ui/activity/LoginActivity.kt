package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityLoginBinding
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.viewmodel.LoginViewModel
import com.truecaller.android.sdk.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.timer

private const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()
    lateinit var number: String
    lateinit var deviceId: String
    lateinit var referralCode: String
    lateinit var playerId: String
    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        initTrueCaller()
        TruecallerSDK.getInstance().isUsable
        TruecallerSDK.getInstance().getUserProfile(this)
    }

    private fun initListener() {
        binding.referralCode.setOnClickListener {
            binding.referralCode.visibility = View.GONE
            binding.layoutEditReferral.visibility = View.VISIBLE
            binding.buttonLogin.visibility = View.VISIBLE
            binding.trucallerText.visibility = View.GONE
            binding.buttonTruecallerLogin.visibility = View.GONE
        }
        binding.trucallerText.setOnClickListener {
            binding.trucallerText.visibility = View.GONE
            binding.referralCode.visibility = View.GONE
            binding.buttonLogin.visibility = View.GONE
            binding.truecallerReferralCode.visibility = View.VISIBLE
            binding.buttonTruecallerLogin.visibility = View.VISIBLE
        }
        binding.truecallerReferralCode.setOnClickListener {
            binding.trucallerText.visibility = View.GONE
            binding.referralCode.visibility = View.GONE
            binding.buttonLogin.visibility = View.GONE
            binding.truecallerReferralCode.visibility = View.GONE
            binding.layoutEditReferral.visibility = View.VISIBLE
            binding.buttonTruecallerLogin.visibility = View.VISIBLE
        }

        binding.buttonLogin.setOnClickListener {
            /* val intent = Intent(this, VerifyNumberActivity::class.java)
             startActivity(intent)*/
            if (validateFields()) {
                val request =
                    NumberRegisterRequest(MobileNumber = number, DeviceID = deviceId, referralCode)
                viewModel.registerNumberRequest(request)
            }
        }
    }

    private fun initObserver() {
        viewModel.userMobileRegisterResponse.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.data?.let {
                        playerId = it.playerId
                        sharedPreferences = getSharedPreferences(
                        getString(R.string.share_pref),
                        Context.MODE_PRIVATE
                    )
                        editor = sharedPreferences.edit()
                        editor.putString(getString(R.string.sharePref_playerId), it.playerId)
                        editor.putBoolean(getString(R.string.sharedPref_loginStatus), it.alreadyLoggedIn)
                        editor.putInt(getString(R.string.sharedPref_walletId), it.walletId)
                        editor.apply()
                        val request = UserOtpRequest(number, playerId, deviceId)
                        viewModel.sendOtpRequest(request)
                    }
                }
                Status.ERROR -> {
                    Log.e(TAG, "observeLiveData: $response")
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })

        viewModel.userOtpResponse.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                  /*  val data = response.data!!
                    val userDetail = UserDetail(
                        number, playerId, deviceId
                    )*/
                    editor = sharedPreferences.edit()
                    editor.putString("deviceId", deviceId)
                    editor.putString("number", number)
                    val intent = Intent(this, VerifyNumberActivity::class.java)
                    intent.putExtra("number", number)
                    intent.putExtra("playerId", playerId)
                    intent.putExtra("deviceId", deviceId)
                    intent.putExtra("referralCode", referralCode)
                    startActivity(intent)
                }
                Status.ERROR -> {
                    Log.e(TAG, "observeLiveData: $response")
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        number = binding.editNumber.text.toString().trim()
        referralCode = binding.editReferralCode.text.toString().trim()
        deviceId = UUID.randomUUID().toString()

        if (number.isEmpty()) {
            Toast.makeText(this, "Phone number can't be empty", Toast.LENGTH_SHORT).show()
            isAllFieldValidate = false
        } else if (number.length < 10) {
            Toast.makeText(this, "Please enter 10 digit number", Toast.LENGTH_SHORT).show()
            isAllFieldValidate = false
        }
        return isAllFieldValidate
    }

    fun initTrueCaller() {
        val trueScope = TruecallerSdkScope.Builder(this, sdkCallback)
            .consentMode(TruecallerSdkScope.CONSENT_MODE_POPUP)
            .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_VERIFY)
            .footerType(TruecallerSdkScope.FOOTER_TYPE_SKIP)
            .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITH_OTP)
            .build()
        TruecallerSDK.init(trueScope)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                TruecallerSDK.getInstance().isUsable
                TruecallerSDK.getInstance()
                    .onActivityResultObtained(this, requestCode, resultCode, data)
            }
        }
    }

    object sdkCallback : ITrueCallback {
        override fun onSuccessProfileShared(success: TrueProfile) {
            Log.i("LoginActivity","onSuccess :{${success.accessToken}}")
            val accessToken = success.accessToken
            val endPoint = "https://api4.truecaller.com/v1/otp/installation/phoneNumberDetail/{$accessToken}"

        }

        override fun onFailureProfileShared(error: TrueError) {
            Log.i("LoginActivity","onFailure ${error.errorType} :: $error")
        }

        override fun onVerificationRequired(p0: TrueError?) {
            Log.i("LoginActivity","onVerification")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TruecallerSDK.clear()
    }

}