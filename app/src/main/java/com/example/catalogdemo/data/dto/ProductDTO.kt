package com.example.catalogdemo.data.dto

data class ProductDTO(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
    val price: Float,
    val rating: Float,
)