package com.example.chatbuddy.repositories


import com.example.chatbuddy.model.Message
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addMessage(
        chatKey: String,
        message: Message,
        onCompleteListener: OnCompleteListener<Void>
    ) {
        val chatRef = db.collection("Chats").document(chatKey)
        val messageRef = chatRef.collection("messages").document()

        messageRef.set(message)
            .addOnCompleteListener(onCompleteListener)
    }
}


