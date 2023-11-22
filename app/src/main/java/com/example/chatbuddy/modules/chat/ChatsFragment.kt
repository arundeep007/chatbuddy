package com.example.chatbuddy.modules.chat

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.BaseFragment
import com.example.chatbuddy.adapters.ChatAdapter
import com.example.chatbuddy.databinding.FragmentChatsBinding
import com.example.chatbuddy.model.Message
import com.example.chatbuddy.model.User
import com.example.chatbuddy.repositories.ChatRepository
import com.example.chatbuddy.utils.loadImageFromUri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ChatsFragment : BaseFragment() {
    private var binding: FragmentChatsBinding? = null
    lateinit var adapter: ChatAdapter

    private lateinit var chatRepository: ChatRepository

    private lateinit var currentUserId: String
    var chatKey: String? = null
    private lateinit var user2: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUserId = getUserId()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // Retrieve the bundle from arguments
        val bundle = arguments
        // Check if the bundle exists and contains the user object
        if (bundle != null && bundle.containsKey("user")) {
            user2 = bundle.getSerializable("user") as User

        }




        binding = FragmentChatsBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize values and onClicks
        chatKey = makechatKey()

        inits()

    }

    private fun inits() {

        initMessageListner()

        chatRepository = ChatRepository()
        binding?.tvUser2Name?.text = user2.username
        binding?.ivUser2?.loadImageFromUri(Uri.parse(user2.profileUrl))
        binding?.btBack?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.clSend?.setOnClickListener {
            //check if chat id is created or not
            //if not created user send message first time then
            val message = Message(binding?.editTextMessage?.text.toString(), currentUserId)
            if (chatKey != null) {
                chatKey = makechatKey()
            } else {
                //if chat key is generated

            }
            binding?.editTextMessage?.setText("")

            chatRepository.addMessage(chatKey!!, message, OnCompleteListener<Void> { task ->
                if (task.isSuccessful) {
                    // The message was successfully added


                    scrollToLastMessage()

                } else {
                    // There was an error adding the message

                }
            })
        }

    }

    private fun initMessageListner() {
        val chatRef = sessionManger.getDb().collection("Chats").document(chatKey!!)
        val messagesRef = chatRef.collection("messages")

        val query = messagesRef.orderBy("timestamp")

        try {
            query.addSnapshotListener { value, error ->

                val messageList = mutableListOf<Message>()

                for (document in value?.documents!!) {
                    val messageData = document.data
                    if (messageData != null) {

                        val timestamp = messageData["timestamp"] as Timestamp
                        val value = convertFormat(timestamp)
                        val message = Message(
                            message = messageData["message"] as String,
                            sendBY = messageData["sendBY"] as String,
                            time= value

                        )
                        messageList.add(message)
                    }
                }


                //  messageList.sortByDescending { it.timestamp }
                adapter = ChatAdapter(messageList)

                binding?.recyclerViewChat?.adapter = adapter
                scrollToLastMessage()

                // Now you have a List<Message> containing your messages
                // You can use this list to populate your RecyclerView or perform other operations
            }
        } catch (e: Exception) {
            Log.e(TAG, "exception in MessageSnapshotListner : ${e.message}", e)
        }


    }


    private fun makechatKey(): String {

        var chatKey = ""

        val currentId = getUserId()
        val receiverId = user2.id
        val result = currentId.compareTo(receiverId)

        if (result != null) {
            if (result > 0) {
                chatKey = currentId + receiverId
            } else {
                chatKey = receiverId + currentId
            }
        }

        return chatKey

    }

    fun scrollToLastMessage() {
        if (adapter.itemCount > 0) {
            // Scroll to the last item in the adapter
            binding?.recyclerViewChat?.scrollToPosition(adapter.itemCount - 1)
        }


    }

    fun convertFormat(timestamp: Timestamp): String {
        // Convert Timestamp to Date
        val date = timestamp.toDate()

// Format the Date to extract the time in 12-hour format with AM/PM
        val timeFormat =
            SimpleDateFormat("h:mm a", Locale.US) // Use Locale for language-specific formatting

        return timeFormat.format(date)
    }

}