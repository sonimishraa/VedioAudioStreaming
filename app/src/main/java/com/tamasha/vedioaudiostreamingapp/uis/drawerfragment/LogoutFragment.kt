package com.tamasha.vedioaudiostreamingapp.uis.drawerfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.uis.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val title = SpannableString("Logout?")
        title.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            title.length,
            0
        )
        builder.setTitle(title)
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "You are Logged Out", Toast.LENGTH_SHORT).show()
                getLoginScreen()

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                // Toast.makeText(context, "Cancel Button Cliked", Toast.LENGTH_SHORT).show()
            })

        return builder.create()
    }

    private fun getLoginScreen() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                getString(R.string.share_pref),
                Context.MODE_PRIVATE
            )
        val loggedInStatus = sharedPreferences.getBoolean(resources.getString(R.string.sharedPref_loginStatus), true)
        val authToken = sharedPreferences.getString(resources.getString(R.string.sharedPref_authToken),"")
        Log.i("LogoutFragment", "LoggedInStatus:${loggedInStatus}")
        sharedPreferences.edit().clear().commit()
        sharedPreferences.edit().clear().commit()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

}