package com.example.catalogdemo.domain.repository

import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem

interface Repository {

    suspend fun getProductList(): List<ProductItem>
    suspend fun getProductDetail(id: Int) : ProductDetail
    suspend fun searchProduct(query: String) : List<ProductItem>
}