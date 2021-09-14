package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, VerifyNumberActivity::class.java)
            startActivity(intent)
        }
    }

}