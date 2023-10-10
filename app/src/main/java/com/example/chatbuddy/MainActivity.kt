package com.example.chatbuddy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.chatbuddy.utils.SessionManager

class MainActivity : AppCompatActivity() {

    lateinit var sessionManger:SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        sessionManger=SessionManager(context)

       // setStatusOnline()
        return super.onCreateView(name, context, attrs)


    }

    override fun onPause() {
        super.onPause()

        //setStatusOffline()
    }




    private fun setStatusOnline() {

        // Define the updated user data
        val updatedStatus = "New Status"
        val updatedProfileUrl = "https://example.com/new_profile_url"

// Specify the document reference for the user you want to update (replace "123" with the actual user ID)
        val userDocumentRef = sessionManger.getDb().collection("users").document(sessionManger.getUserId()!!)

// Create a map with the fields you want to update
        val updates = hashMapOf(
            "status" to "online",
        )

// Update the document with the new data
        userDocumentRef
            .update(updates as Map<String, Any>)
            .addOnSuccessListener {
                // Data was successfully updated
                println("User data updated successfully.")
            }
            .addOnFailureListener { e ->
                // Handle errors here
                println("Error updating user data: $e")
            }
    }



    private fun setStatusOffline()
    {
        // Define the updated user data
        val updatedStatus = "New Status"
        val updatedProfileUrl = "https://example.com/new_profile_url"

// Specify the document reference for the user you want to update (replace "123" with the actual user ID)
        val userDocumentRef = sessionManger.getDb().collection("users").document(sessionManger.getUserId()!!)

// Create a map with the fields you want to update
        val updates = hashMapOf(
            "status" to "offline",
        )

// Update the document with the new data
        userDocumentRef
            .update(updates as Map<String, Any>)
            .addOnSuccessListener {
                // Data was successfully updated
                println("User data updated successfully.")
            }
            .addOnFailureListener { e ->
                // Handle errors here
                println("Error updating user data: $e")
            }
    }


}