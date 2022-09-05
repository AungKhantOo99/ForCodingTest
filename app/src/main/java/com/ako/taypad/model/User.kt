package com.ako.taypad.model

data class User(
    val blocked: Boolean,
    val confirmed: Boolean,
    val createdAt: String,
    val email: String,
    val id: Int,
    val provider: String,
    val updatedAt: String,
    val username: String
)