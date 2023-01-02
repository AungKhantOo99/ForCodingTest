package com.ako.taypad.model.getpartsdata

data class Data(
    val categories: List<Any>,
    val contentRating: Boolean,
    val coverUrl: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val parts: List<Part>,
    val status: Any,
    val title: String,
    val updatedAt: String
)