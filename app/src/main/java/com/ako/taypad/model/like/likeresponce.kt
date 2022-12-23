package com.ako.taypad.model.like

data class likeresponce(
    val comments: List<Comment>,
    val commentsCount: Int,
    val contentMenu: ContentMenu,
    val id: String,
    val likeCount: String,
    val partInfo: PartInfo,
    val readCount: String
)