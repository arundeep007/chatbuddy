package com.example.chatbuddy.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.chatbuddy.R

class CustomAlertDialog(private val context: Context) {

    private val view: View

    companion object{
          var alertDialog: AlertDialog?=null
    }
    init {
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.layout_progress_bar, null)

        // Create the AlertDialog builder
        val builder = AlertDialog.Builder(context,R.style.TransparentDialog)
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.setCancelable(true)

    }

    fun show() {
        alertDialog?.show()
    }
    fun hide()
    {
        alertDialog?.hide()
    }
    fun setCancelable(flag:Boolean)
    {
        alertDialog?.setCancelable(flag)
    }
}