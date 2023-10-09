package com.example.chatbuddy.modules.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.BaseFragment
import com.example.chatbuddy.R
import com.example.chatbuddy.databinding.FragmentLoginBinding
import com.example.chatbuddy.databinding.FragmentSignUpBinding
import com.example.chatbuddy.utils.AppUtils
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding?=null
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCallbacks()
    }

    private fun initCallbacks() {

        binding?.tvSignUp?.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)


        }

        binding?.btLogin?.setOnClickListener {
            checkValidations()
        }


    }

    private fun checkValidations() {

        val email: String = binding?.etEmail?.text.toString()
        val password: String = binding?.etPassword?.text.toString()

        when(true)
        {
            email.isEmpty()-> AppUtils.showToast("Email is Mandatory!",context)
            password.isEmpty()-> AppUtils.showToast("Password is Mandatory!",context)
            else -> {

                when(true)

                {
                    (!Patterns.EMAIL_ADDRESS.matcher(email).matches())->AppUtils.showToast("Please enter a Valid Email Address",context)
                    (password.length<=6)->AppUtils.showToast("Password length should be greater than 6 !",context)

                    else -> {
                        loginUser(email,password)
                    }
                }


            }
        }

    }

    private fun loginUser(email: String, password: String) {
        
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { 
            
            task->
            if (task.isSuccessful)
            {
                AppUtils.showToast("Login Successful",context)

            }else
            {
                AppUtils.showToast("Email and Password doesn't match",context)
            }
        }

    }


}