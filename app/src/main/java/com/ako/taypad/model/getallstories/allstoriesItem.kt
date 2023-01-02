package com.ako.taypad.model.getallstories

data class allstoriesItem(
    val author: Author?,
    val category: Category,
    val contentRating: Boolean,
    val copyright: Any,
    val coverUrl: String?,
    val createdAt: String,
    val createdBy: CreatedBy,
    val description: String,
    val id: Int,
    val parts: List<Any>,
    val status: String,
    val title: String,
    val updatedAt: String,
    val updatedBy: UpdatedBy
)