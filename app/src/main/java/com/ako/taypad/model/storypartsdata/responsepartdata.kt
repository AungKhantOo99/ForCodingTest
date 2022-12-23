package com.ako.taypad.model.storypartsdata

data class responsepartdata(
    val comments: ArrayList<Comment>,
    val commentsCount: Int,
    val contentMenu: ContentMenu,
    val id: String,
    val likeCount: String,
    val partInfo: PartInfo,
    val readCount: String
)