package com.example.chatbuddy.modules.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatbuddy.R
import com.example.chatbuddy.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var binding: FragmentMessageBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMessageBinding.inflate(layoutInflater)
        return binding?.root
    }

}