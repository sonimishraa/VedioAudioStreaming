package com.tamasha.vedioaudiostreamingapp.uis

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityVerifyNumberBinding
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.request.VerifyOtpRequest
import com.tamasha.vedioaudiostreamingapp.network.SMSReceiver
import com.tamasha.vedioaudiostreamingapp.network.SmsBroadCastReceiver
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.viewmodel.LoginViewModel
import com.tamasha.vedioaudiostreamingapp.viewmodel.VerifyOtpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

const val REQUEST_USER_CONTENT = 200

private const val TAG = "VerifyOTPActivity"

@AndroidEntryPoint
class VerifyNumberActivity : BaseActivity() {

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SMSReceiver? = null

    lateinit var smsBroadCastReceiver: SmsBroadCastReceiver
    lateinit var firstDigit: String
    lateinit var secondDigit: String
    lateinit var thirdDigit: String
    lateinit var forthDigit: String
    lateinit var number: String
    lateinit var deviceId: String
    lateinit var playerId: String
    lateinit var referralCode: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityVerifyNumberBinding
    val viewModel: VerifyOtpViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        //smartUserConsent()
        initListener()
        initObserver()
    }

    private fun initView() {
        val bundle = intent.extras
        number = bundle?.getString("number").toString()
        deviceId = bundle?.getString("deviceId").toString()
        playerId = bundle?.getString("playerId").toString()
        referralCode = bundle?.getString("referral").toString()
    }

    private fun initListener() {
        binding.btnConfirm.setOnClickListener {
            verifyOtp()
        }
        binding.resendOtp.setOnClickListener {
            resendOtp()
        }

        binding.firstDigit.doOnTextChanged { text, start, before, count ->
            if (binding.firstDigit.text.toString().length == 1) {
                binding.secondDigit.requestFocus()
            }
        }
        binding.secondDigit.doOnTextChanged { text, start, before, count ->
            if (binding.secondDigit.text.toString().length == 1 && binding.firstDigit.text.toString().length == 1) {
                binding.thirdDigit.requestFocus()
            }
        }
        binding.thirdDigit.doOnTextChanged { text, start, before, count ->
            if (binding.thirdDigit.text.toString().length == 1 && binding.secondDigit.text.toString().length == 1 && binding.firstDigit.text.toString().length == 1) {
                binding.forthDigit.requestFocus()
            }
        }
    }

    private fun verifyOtp() {
        if (validateFields()) {
            showLoading()
            val clientOtp = firstDigit + secondDigit + thirdDigit + forthDigit
            val verifyOtpRequest = VerifyOtpRequest(
                ClientOTP = clientOtp, MobileNumber = number,
                UserId = playerId, DeviceID = deviceId
            )
            viewModel.verifyOtpRequest(verifyOtpRequest)
        }
    }

    private fun resendOtp() {
        showLoading()
        val request =
            NumberRegisterRequest(MobileNumber = number, DeviceID = deviceId, referralCode)
        loginViewModel.registerNumberRequest(request)
    }

    private fun initObserver() {
        viewModel.verifyOtpResponse.observe(this, { response ->
            dismissLoading()
            when (response.status) {
                Status.SUCCESS -> {
                    if(response.data?.error?.code == "0") {
                        val authToken = response.data.authToken
                        sharedPreferences = getSharedPreferences(
                            getString(R.string.share_pref),
                            Context.MODE_PRIVATE
                        )
                        editor = sharedPreferences.edit()
                        editor.putString(getString(R.string.sharedPref_authToken), authToken)
                        response.data.newUser?.let {
                            editor.putBoolean(
                                getString(R.string.sharedPref_newUserStatus),
                                it
                            )
                        }
                        editor.apply()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Enter correct OTP", Toast.LENGTH_SHORT).show()
                    }
                }
                Status.ERROR -> {
                    Log.e(TAG, "observeLiveData: ${response}")
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
        loginViewModel.userMobileRegisterResponse.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.data?.let {
                        playerId = it.playerId
                        val request = UserOtpRequest(number, playerId, deviceId)
                        loginViewModel.sendOtpRequest(request)
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

        loginViewModel.userOtpResponse.observe(this, { response ->
            dismissLoading()
            when (response.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "Resend OTP Successfully", Toast.LENGTH_SHORT).show()
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

}


    /* private fun smartUserConsent() {
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
         registerReceiver(smsBroadCastReceiver, intentFilter)
     }

     override fun onStart() {
         registerBroadCastReceiver()
         super.onStart()

     }*/
