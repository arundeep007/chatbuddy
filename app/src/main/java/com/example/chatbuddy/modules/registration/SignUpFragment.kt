package com.example.chatbuddy.modules.registration

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.BaseFragment
import com.example.chatbuddy.R
import com.example.chatbuddy.databinding.FragmentSignUpBinding
import com.example.chatbuddy.model.User
import com.example.chatbuddy.utils.AppUtils
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : BaseFragment() {
    private var binding: FragmentSignUpBinding? = null
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCallbacks()

    }

    private fun initCallbacks() {
        binding?.tvLogin?.setOnClickListener {


            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding?.btSignUp?.setOnClickListener {

            checkValidations()


        }


    }

    private fun checkValidations() {

        // Getting values from edit texts
        val username: String = binding?.etUsername?.text.toString()
        val email: String = binding?.etEmail?.text.toString()
        val password: String = binding?.etPassword?.text.toString()
        val confirmPassword: String = binding?.etConfirmPassword?.text.toString()


        when (true) {
            //checking all the fields are filled
            username.isEmpty() -> AppUtils.showToast("Username is Mandatory!", context)
            email.isEmpty() -> AppUtils.showToast("Email is Mandatory!", context)
            password.isEmpty() -> AppUtils.showToast("Password is Mandatory!", context)
            confirmPassword.isEmpty() -> AppUtils.showToast(
                "Confirm Password is Mandatory!", context
            )
            else -> {

                when (true) {
                    (username.length <= 6) -> AppUtils.showToast(
                        "Username length should be greater than 6 !", context
                    )

                    (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> AppUtils.showToast(
                        "Please enter a Valid Email Address", context
                    )

                    (password.length <= 6) -> AppUtils.showToast(
                        "Password length should be greater than 6 !", context
                    )

                    (password != confirmPassword) -> AppUtils.showToast(
                        "Password and confirm Password are equal!", context
                    )
                    else -> {
                        //creating progress bar dialog when needed
                        /* if (CustomAlertDialog.alertDialog!=null)
                         {
                             CustomAlertDialog.alertDialog?.show()

                         }else{
                             CustomAlertDialog(context!!)
                         }*/

                        showLoadingAlert()

                        signUp(username, email, password)


                    }
                }

            }
        }


    }

    private fun signUp(username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            //CustomAlertDialog.alertDialog?.hide()

            hideLoadingAler()

            if (task.isSuccessful) {
                AppUtils.showToast("User Created", context)
                //Store user in database

                storeUser(email.replace(".", ""), username)


            } else {
                val message = task.exception?.message.toString()

                when (true) {
                    message.contains("The email address is already") -> AppUtils.showToast(
                        "Email is already in use. Please sign in or reset your password.", context
                    )
                    else -> {
                        AppUtils.showToast("Something went wrong", context)

                    }
                }
            }
        }

    }

    private fun storeUser(id: String, username: String) {

        // Create a new user object

        val user = User(
            username = username, status = "online", profileUrl = "", id = id
        )

        // Add the user data to Firestore

        sessionManger.getDb().collection("users")
            .document(id) // Use the desired document ID, e.g., user's ID
            .set(user).addOnSuccessListener {

                //Go to Listing Users Screen

                sessionManger.saveUser(user)
                findNavController().navigate(R.id.action_signUpFragment_to_usersFragment)
            }.addOnFailureListener { e ->
                // Handle errors here
            }


    }


}


