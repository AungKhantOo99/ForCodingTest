package com.ako.taypad.model.skipaction

import java.io.Serializable

data class responsedata(
    val contentRating: Boolean,
    val coverUrl: Any,
    val createdAt: String,
    val description: Any,
    val id: Int,
    val status: Any,
    val title: String,
    val updatedAt: String
) :Serializable