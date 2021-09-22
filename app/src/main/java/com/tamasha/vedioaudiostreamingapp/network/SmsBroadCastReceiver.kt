package com.tamasha.vedioaudiostreamingapp.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint

class SmsBroadCastReceiver() : BroadcastReceiver() {

    /*override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras: Bundle = intent.extras!!
            val smsRetrieverStatus: Status = extras.get(SmsRetriever.EXTRA_STATUS) as Status

            when (smsRetrieverStatus.getStatusCode()) {
                CommonStatusCodes.SUCCESS -> {
                    val messageIntent: Intent =
                        extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT)!!
                    listener.onSuccess(messageIntent)
                }
                CommonStatusCodes.TIMEOUT -> {
                    listener.onFailure()
                }
            }

        }

    }

    interface smsBroadCastListener {
        fun onSuccess(intent: Intent)

        fun onFailure()
    }*/


    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val  message: String? = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                }          // Get SMS message contents

                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }
    }
}