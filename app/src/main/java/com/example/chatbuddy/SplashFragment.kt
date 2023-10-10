package com.example.chatbuddy

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class SplashFragment : Fragment() {

    private var isLogged=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserStatus()

    }

    private fun checkUserStatus() {
        val session = SessionManager(requireContext())

        if (session.isLoggedIn())
        {
            isLogged=true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        Handler().postDelayed(Runnable {

            if (isLogged)
            {
                findNavController().navigate(R.id.action_splashFragment_to_usersFragment)
            }else
            {
                findNavController().navigate(R.id.action_splashFragment_to_signUpFragment)

            }


        }, 2500)



    }


}