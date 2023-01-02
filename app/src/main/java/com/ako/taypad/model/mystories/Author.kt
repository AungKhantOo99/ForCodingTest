package com.ako.taypad.model.mystories

data class Author(
    val blocked: Boolean,
    val confirmationToken: Any,
    val confirmed: Boolean,
    val createdAt: String,
    val email: String,
    val id: Int,
    val password: String,
    val phoneNumber: Any,
    val profilePicUrl: Any,
    val provider: String,
    val resetPasswordToken: Any,
    val updatedAt: String,
    val username: String
)