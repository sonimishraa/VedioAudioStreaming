package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityVerifyNumberBinding
import com.tamasha.vedioaudiostreamingapp.model.request.VerifyOtpRequest
import com.tamasha.vedioaudiostreamingapp.network.SmsBroadCastReceiver
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.viewmodel.VerifyOtpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

const val REQUEST_USER_CONTENT = 200

private const val TAG = "VerifyOTPActivity"

@AndroidEntryPoint
class VerifyNumberActivity : AppCompatActivity() {

    lateinit var smsBroadCastReceiver: SmsBroadCastReceiver
    lateinit var firstDigit: String
    lateinit var secondDigit: String
    lateinit var thirdDigit: String
    lateinit var forthDigit: String
    lateinit var number: String
    lateinit var deviceId: String
    lateinit var playerId: String
    private lateinit var binding: ActivityVerifyNumberBinding
    val viewModel: VerifyOtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
         number = bundle?.getString("number").toString()
        deviceId = bundle?.getString("deviceId").toString()
        playerId = bundle?.getString("playerId").toString()

        smartUserConsent()
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.btnConfirm.setOnClickListener {
            verifyOtp()
        }
    }

    private fun verifyOtp() {
        if (validateFields()) {
            val clientOtp = firstDigit + secondDigit + thirdDigit + forthDigit
            val verifyOtpRequest = VerifyOtpRequest(
                ClientOTP = clientOtp, MobileNumber = number,
                UserId = playerId, DeviceID = deviceId
            )
            viewModel.verifyOtpRequest(verifyOtpRequest)
        }
    }

    private fun initObserver() {
        viewModel.verifyOtpResponse.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "${response.data?.authToken}", Toast.LENGTH_SHORT).show()
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
        firstDigit = binding.firstDigit.text.toString().trim()
        secondDigit = binding.secondDigit.text.toString().trim()
        thirdDigit = binding.thirdDigit.text.toString().trim()
        forthDigit = binding.forthDigit.text.toString().trim()
        return isAllFieldValidate
    }

    private fun smartUserConsent() {
        val client: SmsRetrieverClient = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_USER_CONTENT) {
            if (resultCode == RESULT_OK && data != null) {
                val message: String = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE).toString()
                getOtpFromMessage(message)

            }
        }
    }

    private fun getOtpFromMessage(message: String) {
        val otpPattern: Pattern = Pattern.compile("(|^)\\d{4}")
        val matcher: Matcher = otpPattern.matcher(message)
        if (matcher.find()) {
            matcher.group(0)
        }
    }

    private fun registerBroadCastReceiver(
    ) {
        smsBroadCastReceiver =
            SmsBroadCastReceiver(object : SmsBroadCastReceiver.smsBroadCastListener {
                override fun onSuccess(intent: Intent) {
                    startActivityForResult(intent, REQUEST_USER_CONTENT)
                }

                override fun onFailure() {
                }
            })
        val intentFilter: IntentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        //registerBroadCastReceiver(smsBroadCastReceiver, intentFilter)
    }

    override fun onStart() {
        registerBroadCastReceiver()
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
    }
}