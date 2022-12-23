package com.ako.taypad.model.writeparts.responseparts

data class responsepartsdata(
    val content: String,
    val createdAt: String,
    val createdBy: Any,
    val id: Int,
    val partCoverUrl: String,
    val publishedTime: Any,
    val status: String,
    val story: Story,
    val title: String,
    val updatedAt: String,
    val updatedBy: Any
)