package com.ako.taypad.model.storypartsdata

data class ContentMenu(
    val author: Author,
    val categories: List<Category>,
    val contentRating: Boolean,
    val copyright: Any,
    val description: String,
    val id: Int,
    val parts: List<PartX>,
    val status: String,
    val title: String
)