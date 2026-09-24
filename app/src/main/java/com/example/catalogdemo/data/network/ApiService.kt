package com.example.catalogdemo.data.network

import com.example.catalogdemo.data.dto.ProductDTO
import com.example.catalogdemo.data.dto.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/products")
    suspend fun getProductListAPI(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int
    ) : ResponseDTO

    @GET("/products/{id}")
    suspend fun getProductDetailAPI(@Path("id") id: Int) : ProductDTO

    @GET("/products/search")
    suspend fun searchProduct(
        @Query("q") query: String) : ResponseDTO

}