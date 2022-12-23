package com.ako.taypad.model.storypartsdata

data class Comment(
    val comment: String,
    val commentBy: CommentBy,
    val commentTime: String,
    val createdAt: String,
    val createdBy: Any,
    val id: Int,
    val part: Part,
    val updatedAt: String,
    val updatedBy: Any
)