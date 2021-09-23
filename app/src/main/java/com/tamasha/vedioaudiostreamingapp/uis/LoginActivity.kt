package com.tamasha.vedioaudiostreamingapp.uis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityLoginBinding
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.TrueCallerRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.network.SMSReceiver
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.viewmodel.LoginViewModel
import com.truecaller.android.sdk.*
import com.truecaller.android.sdk.clients.VerificationDataBundle
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import androidx.core.app.ActivityCompat.startIntentSenderForResult

import com.google.android.gms.auth.api.Auth

import android.app.PendingIntent

import com.google.android.gms.auth.api.credentials.HintRequest
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.credentials.Credential

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener

import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.Task


private const val TAG = "LoginActivity"

const val RESOLVE_HINT = 100
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SMSReceiver? = null

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()
    var requestCode: Int = 1
    lateinit var number: String
    lateinit var deviceId: String
    lateinit var referralCode: String
    lateinit var playerId: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var accessToken: String
    lateinit var otpHash: String
    lateinit var firstName: String
    lateinit var lastName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSmsListener()
       // initBroadCast()
        //initView()
        initListener()
        initObserver()
        // trueCallerAccessToken()
    }

    private fun initListener() {
        binding.referralCode.setOnClickListener {
            binding.referralCode.visibility = View.GONE
            binding.editReferralCode.visibility = View.VISIBLE
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
            binding.editReferralCode.visibility = View.VISIBLE
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
        binding.buttonTruecallerLogin.setOnClickListener {
            accessToken = VerificationDataBundle().profile?.accessToken.toString()
            val endpoint =
                "https://api4.truecaller.com/v1/otp/installation/phoneNumberDetail/{$accessToken}"
            val trueCallerRequest = TrueCallerRegisterRequest(requestId = "", accessToken, endpoint)
            viewModel.trueCallerRegisterRequest(trueCallerRequest)
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
                        editor.putBoolean(
                            getString(R.string.sharedPref_loginStatus),
                            it.alreadyLoggedIn
                        )
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

        viewModel.truecallerRegister.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "Logged in with TrueCaller", Toast.LENGTH_SHORT).show()
                    /* editor = sharedPreferences.edit()
                     editor.putString("deviceId", deviceId)
                     editor.putString("number", number)
                     val intent = Intent(this, VerifyNumberActivity::class.java)
                     intent.putExtra("number", number)
                     intent.putExtra("playerId", playerId)
                     intent.putExtra("deviceId", deviceId)
                     intent.putExtra("referralCode", referralCode)*/
                    val intent = Intent(this, HomeActivity::class.java)
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

  /*  private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent = Auth.CredentialsApi.getHintPickerIntent(
            apiClient, hintRequest
        )
        startIntentSenderForResult(
            intent.intentSender,
            RESOLVE_HINT, null, 0, 0, 0
        )
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
                // credential.getId();  <-- will need to process phone number string
            }
        }
    }

    fun initSmsListener(){
        val client = SmsRetriever.getClient(this /* context */)

// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.

// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task: Task<Void> = client.startSmsRetriever()

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(OnSuccessListener<Void?> {
            // Successfully started retriever, expect broadcast intent
            // ...
        })

        task.addOnFailureListener(OnFailureListener {
            // Failed to start retriever, inspect Exception for more details
            // ...
        })
}
/*

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = SMSReceiver()
        smsReceiver?.setOTPListener(object : SMSReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                showToast("$otp")
            }
        })
    }
*/

   /* private fun initSmsListener() {
        val client = SmsRetriever.getClient(this)
        client.startSmsRetriever()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(smsReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsReceiver)
    }

    private fun showToast(msg: String?) {
        if (msg != null) {
            otpHash = msg
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
*/
    /*  fun initTrueCaller() {
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
              val activity: Activity = LoginActivity()
              activity.runOnUiThread {
                  //Toast.makeText(activity, "accessToken:{${success.accessToken}}", Toast.LENGTH_SHORT).show()
                  *//* accessToken = success.accessToken
                val endPoint = "https://api4.truecaller.com/v1/otp/installation/phoneNumberDetail/{$accessToken}"
*//*
            }
            Log.i("LoginActivity", "onSuccess :{${success.accessToken}}")

        }

        override fun onFailureProfileShared(error: TrueError) {
            Log.i("LoginActivity", "onFailure ${error.errorType} :: $error")
        }

        override fun onVerificationRequired(error: TrueError?) {
            Log.i("LoginActivity", "onVerification")
        }
    }

    object apiCallback : VerificationCallback {
        override fun onRequestSuccess(requestCode: Int, extras: VerificationDataBundle?) {
            if (requestCode == VerificationCallback.TYPE_MISSED_CALL_INITIATED) {
                if (extras != null)
                    extras.getString(VerificationDataBundle.KEY_TTL)
            }

            if (requestCode == VerificationCallback.TYPE_MISSED_CALL_RECEIVED) {

            }

            if (requestCode == VerificationCallback.TYPE_OTP_INITIATED) {
                if (extras != null) {
                    extras.getString(VerificationDataBundle.KEY_TTL)
                }
            }

            if (requestCode == VerificationCallback.TYPE_OTP_RECEIVED) {

            }

            if (requestCode == VerificationCallback.TYPE_VERIFICATION_COMPLETE) {
                if (extras != null) {
                    extras.getString(VerificationDataBundle.KEY_ACCESS_TOKEN)
                    Log.i("LoginActivity", "accessToken:{${extras.profile?.accessToken}")
                }

            }

            if (requestCode == VerificationCallback.TYPE_PROFILE_VERIFIED_BEFORE) {
                extras?.getProfile()?.accessToken

            }
        }

        override fun onRequestFailure(requestCode: Int, e: TrueException) {

        }
    }

    private fun trueCallerAccessToken() {
        initTrueCaller()
        TruecallerSDK.getInstance().isUsable
        TruecallerSDK.getInstance().getUserProfile(this)
        if (validateFields()) {
            TruecallerSDK.getInstance().requestVerification("+91", number, apiCallback, this)
            val profile: TrueProfile = TrueProfile.Builder(firstName, lastName).build()
            TruecallerSDK.getInstance().verifyMissedCall(profile, apiCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TruecallerSDK.clear()
    }

    private fun showToast(text: String) {
        val activity: Activity = LoginActivity()
        if (activity != null) {
            activity.runOnUiThread { Toast.makeText(activity, text, Toast.LENGTH_SHORT).show() }
        }
    }
*/
}