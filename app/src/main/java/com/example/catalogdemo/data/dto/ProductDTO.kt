package com.example.catalogdemo.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("price") val price: Float,
    @SerializedName("rating") val rating: Float
)

