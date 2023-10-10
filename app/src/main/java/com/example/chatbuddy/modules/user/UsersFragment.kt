package com.example.chatbuddy.modules.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.BaseFragment
import com.example.chatbuddy.R
import com.example.chatbuddy.adapters.UserAdapter
import com.example.chatbuddy.databinding.FragmentUsersBinding
import com.example.chatbuddy.model.User
import com.example.chatbuddy.repositories.UserRepository
import com.example.chatbuddy.utils.OnItemClickListener
import com.google.firebase.firestore.FieldValue
import com.google.firestore.v1.DocumentTransform
import com.google.firestore.v1.DocumentTransform.FieldTransform.ServerValue

class UsersFragment : BaseFragment(), OnItemClickListener {
    private var binding: FragmentUsersBinding? = null
    lateinit var adapter: UserAdapter
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRepository = UserRepository()

        // To get the initial list of users and listen for updates
        userRepository.getUsers { usersList ->


            adapter = UserAdapter(usersList, this)
            binding?.recyclerview?.adapter = adapter


        }




    }

    override fun onItemClick(userData: User) {

        //sending the user2 data
        val bundle = Bundle()
        bundle.putSerializable("user",userData)

        findNavController().navigate(R.id.action_usersFragment_to_chatsFragment,bundle)
    }


    override fun onPause() {
        super.onPause()

        userRepository.stopListening()
    }

}