package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityVerifyNumberBinding

class VerifyNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}