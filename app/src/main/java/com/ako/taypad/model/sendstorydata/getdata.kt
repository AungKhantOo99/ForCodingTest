package com.ako.taypad.model.sendstorydata

import java.io.Serializable

data class getdata(
    val contentRating: Boolean,
    val coverUrl: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val status: Any?,
    val title: String,
    val updatedAt: String
):Serializable