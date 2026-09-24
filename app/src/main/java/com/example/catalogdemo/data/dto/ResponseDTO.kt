package com.example.catalogdemo.data.dto

import com.google.gson.annotations.SerializedName

data class ResponseDTO (
    @SerializedName("products") val products: List<ProductDTO>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)