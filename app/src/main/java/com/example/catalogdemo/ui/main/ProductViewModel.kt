package com.example.catalogdemo.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.request.ImageRequest
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductDetailUiState {
    data object Idle : ProductDetailUiState
    data object Loading : ProductDetailUiState
    data class Success(val product: ProductDetail) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val navArgs = savedStateHandle.toRoute<Detail>()
    private val productId : Int = navArgs.id

    private val _productDetailState =  MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Idle)
    val productDetail : StateFlow<ProductDetailUiState> get() = _productDetailState.asStateFlow()

    init {
        getSingleItem(productId)
    }

    fun getSingleItem(id: Int) {
        viewModelScope.launch {
            _productDetailState.value = ProductDetailUiState.Loading
            try {
                val product = repository.getProductDetail(id)
                val imageLoader = ImageLoader(context)
                val preloadJobs = product.images.map { url ->
                    async {
                        val request = ImageRequest.Builder(context)
                            .data(url)
                            .build()
                        imageLoader.execute(request)
                    }
                }
                preloadJobs.awaitAll()
                _productDetailState.value = ProductDetailUiState.Success(product)
            } catch (e: Exception) {
                Log.println(Log.ERROR, "Console", "${e.message}")
                _productDetailState.value = ProductDetailUiState.Error(e.message ?: "Failed to load product")
            }
        }
    }
}