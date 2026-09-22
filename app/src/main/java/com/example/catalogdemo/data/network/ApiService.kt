package com.example.catalogdemo.data.network

import com.example.catalogdemo.data.dto.ProductDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/products?limit=20&skip=0")
    suspend fun getProductListAPI() : List<ProductDTO>

    @GET("/products/{id}")
    suspend fun getProductDetailAPI(@Path("Id") id: Int) : ProductDTO

    @GET("/products/search?q={query}")
    suspend fun searchProduct(@Path("query") query: String) : List<ProductDTO>

}