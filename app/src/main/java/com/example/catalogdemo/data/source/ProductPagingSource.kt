package com.example.catalogdemo.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catalogdemo.data.mapper.toProductList
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.domain.model.ProductItem

class ProductPagingSource (
    private val apiService: ApiService
): PagingSource<Int, ProductItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItem> {
        val skip = params.key ?: 0
        val limit = params.loadSize
        return try {
            val responseData = apiService.getProductListAPI(limit, skip)
            val body = responseData.products.map { it.toProductList() }

            LoadResult.Page(
                data = body,
                prevKey = if (skip == 0) null else skip - limit,
                nextKey = if (body.isEmpty() || body.size < limit) null else skip + body.size
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }

}