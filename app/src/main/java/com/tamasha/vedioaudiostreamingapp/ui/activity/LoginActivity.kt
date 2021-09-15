package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityLoginBinding
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.viewmodel.LoginViewModel
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()
    lateinit var number:String
    lateinit var deviceId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.buttonLogin.setOnClickListener {
            if (validateFields()){
                val request = UserOtpRequest(MobileNumber = number, DeviceID = deviceId)
                viewModel.sendOtpRequest(request,application)
            }
        }
    }

    private fun initObserver() {
        viewModel.userLoginResponse.observe(this,{ response ->
            Toast.makeText(this, "${response.message}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, VerifyNumberActivity::class.java)
            startActivity(intent)
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        number = binding.editNumber.text.toString().trim()
        deviceId = UUID.randomUUID().toString()

        return isAllFieldValidate
    }

}