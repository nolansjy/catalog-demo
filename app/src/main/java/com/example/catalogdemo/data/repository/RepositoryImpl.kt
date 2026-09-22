package com.example.catalogdemo.data.repository

import com.example.catalogdemo.data.mapper.toProductDetail
import com.example.catalogdemo.data.mapper.toProductList
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService : ApiService) : Repository {
    override suspend fun getProductList(): List<ProductItem> {
        return apiService.getProductListAPI().map { it.toProductList() }
    }

    override suspend fun getProductDetail(id: Int): ProductDetail {
        return apiService.getProductDetailAPI(id).toProductDetail()
    }

    override suspend fun searchProduct(query: String): List<ProductItem> {
        return apiService.searchProduct(query).map { it.toProductList() }
    }
}