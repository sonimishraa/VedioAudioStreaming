package com.tamasha.vedioaudiostreamingapp.uis

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        Handler().postDelayed({
            val authToken = checkAuthtoken()
            val loggedInStatus = checkLoginStatus()
            if (authToken.isNullOrEmpty() && loggedInStatus) {
                    val mIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(mIntent)
                finish()
            }
            else {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }, 500)
    }

    private fun checkAuthtoken(): String? {
         sharedPreferences =
            getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val authToken = sharedPreferences.getString(getString(R.string.sharedPref_authToken), " ")
        return authToken
    }
    private fun checkLoginStatus(): Boolean {
        sharedPreferences =
            getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val loginStatus = sharedPreferences.getBoolean(getString(R.string.sharedPref_loginStatus),true)
        return loginStatus
    }

}