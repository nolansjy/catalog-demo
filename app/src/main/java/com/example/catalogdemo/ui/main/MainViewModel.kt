package com.example.catalogdemo.ui.main

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.data.repository.ProductPagingSource
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: List<ProductItem>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}


@HiltViewModel
class ProductListVewModel @Inject constructor(
    private val apiService: ApiService,
    private val repository: Repository,
) : ViewModel(){

    val productPageFlow: Flow<PagingData<ProductItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ProductPagingSource(apiService = apiService)
            }
        )
            .flow
            .cachedIn(viewModelScope)

    val textFieldState = TextFieldState()

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()


}