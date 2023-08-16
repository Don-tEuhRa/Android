package com.dongminpark.reborn.Model

data class Product(
    val productId: Int = 0,
    val title: String = "",
    val price: Int = 0,
    val content: String = "",
    val thumbnailUrl: String = "",
    val categoryName: String = "",
    val imageUrl: List<String> = listOf(),
    val isInterested: Boolean = false
)
