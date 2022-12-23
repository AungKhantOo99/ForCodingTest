package com.ako.taypad.model.getallstories

data class Author(
    val blocked: Boolean,
    val confirmationToken: Any,
    val confirmed: Boolean,
    val createdAt: String,
    val email: String,
    val id: Int,
    val password: String,
    val provider: String,
    val resetPasswordToken: Any,
    val updatedAt: String,
    val username: String
)