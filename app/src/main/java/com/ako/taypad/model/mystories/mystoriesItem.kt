package com.ako.taypad.model.mystories

data class mystoriesItem(
    val author: Author,
    val category: Category,
    val contentRating: Boolean,
    val copyright: Any,
    val coverUrl: String?,
    val createdAt: String,
    val createdBy: Any,
    val description: String,
    val id: Int,
    val parts: List<Part>,
    val status: Any,
    val title: String,
    val updatedAt: String,
    val updatedBy: UpdatedBy
)