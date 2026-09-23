package com.example.catalogdemo.domain.model

data class ProductDetail(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val images: List<String>,
    val price: Float,
    val rating: Float
)