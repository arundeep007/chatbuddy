package com.example.chatbuddy

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.utils.CustomAlertDialog

open class BaseFragment: Fragment() {

    lateinit var dialog:CustomAlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = context?.let { CustomAlertDialog(it) }!!
    }

    fun clearBackStack()
    {
        findNavController().popBackStack()
    }

    fun showLoadingAlert()
    {
        dialog?.show()
    }
    fun hideLoadingAler()
    {
        dialog?.hide()
    }

}