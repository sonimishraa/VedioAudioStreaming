package com.tamasha.vedioaudiostreamingapp

import android.app.Application
import android.util.Log
import com.tamasha.vedioaudiostreamingapp.util.AppSignatureHashHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    private val TAG = MainApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        // Generate Hash Key >>>>>
        val appSignatureHashHelper = AppSignatureHashHelper(this)
        Log.e(TAG, "HashKey: " + appSignatureHashHelper.appSignatures[0])
    }
}