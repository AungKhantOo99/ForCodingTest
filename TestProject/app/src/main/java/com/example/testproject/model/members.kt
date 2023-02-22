package com.example.testproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class members(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    val articles: List<Article>,
): Serializable