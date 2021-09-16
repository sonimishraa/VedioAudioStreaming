package com.tamasha.vedioaudiostreamingapp.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

val crashlytics = FirebaseCrashlytics.getInstance()

/**
 * Send a DEBUG log message to logcat and firebase.
 * @param tag Used to identify the source of a log message.  It usually identifies
 *        the class or activity where the log call occurs.
 * @param message The message you would like logged.
 */
fun crashlyticsLog(tag: String, message: String) {
  Log.d(tag, message)
  crashlytics.log("$tag: $message")
}
