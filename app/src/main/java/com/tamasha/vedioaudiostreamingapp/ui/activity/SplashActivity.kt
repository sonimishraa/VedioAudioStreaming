package com.tamasha.vedioaudiostreamingapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tamasha.vedioaudiostreamingapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        Handler().postDelayed({
           /* val authToken = " "
            Log.i("SplashActivity", "authToken:${authToken}")
            if (authToken.isNullOrEmpty()) {*/
                val mIntent = Intent( this@SplashActivity, LoginActivity::class.java)
                startActivity(mIntent)
                finish()
           /* else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
*/
        }, 500)
    }

}