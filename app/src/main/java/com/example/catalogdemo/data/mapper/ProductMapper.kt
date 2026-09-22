package com.example.catalogdemo.data.mapper

import com.example.catalogdemo.data.dto.ProductDTO
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem

fun ProductDTO.toProductList() : ProductItem{
    return ProductItem(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        price = this.price,
    )
}

fun ProductDTO.toProductDetail() : ProductDetail {
    return ProductDetail(
        id = this.id,
        title = this.title,
        description = this.description,
        category = this.category,
        images = this.images,
        price = this.price,
        rating = this.rating
    )
}