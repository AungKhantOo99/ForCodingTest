package com.ako.taypad.model.like

data class ContentMenu(
    val author: Author,
    val categories: List<Any>,
    val contentRating: Boolean,
    val copyright: Any,
    val description: String,
    val id: Int,
    val parts: List<PartX>,
    val status: Any,
    val title: String
)