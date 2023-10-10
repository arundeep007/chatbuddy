package com.example.chatbuddy.model

data class User(
    val username: String,
    val status: String,
    val profileUrl: String,
    val id: String
):java.io.Serializable