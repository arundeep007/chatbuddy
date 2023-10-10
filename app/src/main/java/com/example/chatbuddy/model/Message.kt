package com.example.chatbuddy.model
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Message (
    val message:String,
    val sendBY:String,
    @ServerTimestamp
    val timestamp: Date?=Date())