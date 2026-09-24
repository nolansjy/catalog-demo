package com.example.catalogdemo.ui.main

import android.util.Log
import androidx.compose.foundation.rememberPlatformOverscrollFactory
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.data.repository.ProductPagingSource
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductListUiState {
    data object Idle : ProductListUiState
    data object Loading : ProductListUiState
    data class Success(val product: ProductItem) : ProductListUiState
    data class Error(val message: String) : ProductListUiState
}

@HiltViewModel
class ProductListVewModel @Inject constructor(
    private val apiService: ApiService,
    private val repository: Repository
) : ViewModel(){

    val productPageFlow: Flow<PagingData<ProductItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ProductPagingSource(apiService = apiService)
            }
        )
            .flow
            .cachedIn(viewModelScope)

    private val _productListState =  MutableStateFlow<ProductListUiState>(ProductListUiState.Idle)
    val productList : StateFlow<ProductListUiState> get() = _productListState.asStateFlow()



    fun getSingleItem(id: Int) {
        viewModelScope.launch {
            _productListState.value = ProductListUiState.Loading
            try {
                val product = repository.getProductDetail(id)
                Log.println(Log.INFO, "SOS", "PRODUCT IS: $product")
                val item = ProductItem(
                    id = product.id,
                    title = product.title,
                    thumbnail = "https://cdn.dummyjson.com/product-images/groceries/tissue-paper-box/thumbnail.webp",
                    price = product.price
                )
                _productListState.value = ProductListUiState.Success(item)
            } catch (e: Exception) {
                Log.println(Log.INFO, "SOS", "${e.message}")

                _productListState.value = ProductListUiState.Error(e.message ?: "Failed to load products")
            }
        }
    }
}