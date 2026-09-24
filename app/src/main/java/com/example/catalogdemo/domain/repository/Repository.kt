package com.example.catalogdemo.domain.repository

import androidx.paging.PagingData
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getProductList(limit: Int, skip: Int): List<ProductItem>
    suspend fun getProductDetail(id: Int) : ProductDetail
    suspend fun searchProduct(query: String) : List<ProductItem>
}