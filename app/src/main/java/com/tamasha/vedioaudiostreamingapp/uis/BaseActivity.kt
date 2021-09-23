package com.tamasha.vedioaudiostreamingapp.uis

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import android.app.Dialog
import android.view.Window
import com.tamasha.vedioaudiostreamingapp.R


@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    lateinit var alertDialoge: Dialog

    fun showLoading(){
        alertDialoge = Dialog(this)
        alertDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialoge.setContentView(R.layout.custom_progressbar)
        alertDialoge.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialoge.show()
    }

   /* fun showLoading() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.custom_progressbar, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        alertDialoge = builder.create()
        alertDialoge.show()
    }*/

    fun dismissLoading() {
        alertDialoge.dismiss()
    }


    fun snackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}