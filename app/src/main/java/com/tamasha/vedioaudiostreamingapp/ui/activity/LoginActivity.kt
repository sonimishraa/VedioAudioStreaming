package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityLoginBinding
import com.tamasha.vedioaudiostreamingapp.model.UserDetail
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()
    lateinit var number: String
    lateinit var deviceId: String
    lateinit var referralCode: String
    lateinit var playerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.referralCode.setOnClickListener {
            binding.referralCode.visibility = View.GONE
            binding.layoutEditReferral.visibility = View.VISIBLE
        }

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, VerifyNumberActivity::class.java)
            startActivity(intent)
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
                    val data = response.data!!
                    val userDetail = UserDetail(
                        number, playerId, deviceId
                    )
                    /*  Intent(this, VerifyNumberActivity::class.java).apply {
                          putExtra(ProjectConstants.USER_DETAILS, userDetail)
                          startActivity(this)
                      }*/
                    val intent = Intent(this, VerifyNumberActivity::class.java)
                    intent.putExtra("number", number)
                    intent.putExtra("playerId",playerId)
                    intent.putExtra("deviceId",deviceId)
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

        return isAllFieldValidate
    }

}