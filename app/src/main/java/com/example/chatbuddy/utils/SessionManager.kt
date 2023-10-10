package com.example.chatbuddy.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.chatbuddy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SessionManager(context: Context) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    companion object {
        // Add other session data keys as needed
    }

    // Methods to manage user sessions

    private fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun logoutUser() {
        firebaseAuth.signOut()
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }

    fun getDb(): FirebaseFirestore {
        return db
    }
    fun getAuth(): FirebaseAuth {
        return firebaseAuth
    }



    fun saveUser(user: User) {
        val editor = sharedPrefs.edit()
        editor.putString("username", user.username)
        editor.putString("status", user.status)
        editor.putString("profileUrl", user.profileUrl)
        editor.putString("id", user.id)
        editor.apply()
    }
    fun getUserId():String?
    {
        val id = sharedPrefs.getString("id", null)

        if (id!=null)
        {
            return id
        }else
        {
            return null
        }
    }


    fun getUser(): User? {
        val username = sharedPrefs.getString("username", null)
        val status = sharedPrefs.getString("status", null)
        val profileUrl = sharedPrefs.getString("profileUrl", null)
        val id = sharedPrefs.getString("id", null)

        if (username != null && status != null && profileUrl != null && id != null) {
            return User(username, status, profileUrl, id)
        }

        return null
    }
    // Additional methods to interact with Firebase Firestore
    // For example, you can save and retrieve user data from Firestore here.
}






