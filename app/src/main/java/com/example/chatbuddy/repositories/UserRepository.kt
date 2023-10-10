package com.example.chatbuddy.repositories

import com.example.chatbuddy.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    private var usersListener: ListenerRegistration? = null

    fun getUsers(callback: (List<User>) -> Unit) {
        // Initial fetch of all users
        usersListener = usersCollection.addSnapshotListener { querySnapshot, _ ->
            val usersList = mutableListOf<User>()
            querySnapshot?.documents?.forEach { document ->
                val userId = document.id
                val username = document.getString("username") ?: ""
                val status = document.getString("status") ?: ""
                val profileUrl = document.getString("profileUrl") ?: ""
                val user = User(username, status, profileUrl, userId)
                usersList.add(user)
            }
            callback(usersList)
        }
    }

    fun stopListening() {
        // Call this function when you want to stop listening for updates
        usersListener?.remove()
    }
}