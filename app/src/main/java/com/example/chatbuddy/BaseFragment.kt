package com.example.chatbuddy

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.utils.CustomAlertDialog
import com.example.chatbuddy.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

open class BaseFragment: Fragment() {

    lateinit var dialog:CustomAlertDialog
    lateinit var sessionManger:SessionManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sessionManger=SessionManager(requireContext())

    }

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

    fun showToast(s: String) {

        Toast.makeText(requireContext(),s,Toast.LENGTH_SHORT).show()

    }

    fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.email?.replace(".","") ?:""
    }




}