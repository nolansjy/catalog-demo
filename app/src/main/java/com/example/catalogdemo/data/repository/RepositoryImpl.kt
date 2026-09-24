package com.example.catalogdemo.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.example.catalogdemo.data.mapper.toProductDetail
import com.example.catalogdemo.data.mapper.toProductList
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService : ApiService) : Repository {
    override suspend fun getProductList(limit: Int, skip: Int): List<ProductItem> {
        return apiService.getProductListAPI(limit, skip).products.map { it.toProductList() }
    }

    override suspend fun getProductDetail(id: Int): ProductDetail {
        return apiService.getProductDetailAPI(id).toProductDetail()
    }

    override suspend fun searchProduct(query: String): List<ProductItem> {
        return apiService.searchProduct(query).products.map { it.toProductList() }
    }
}