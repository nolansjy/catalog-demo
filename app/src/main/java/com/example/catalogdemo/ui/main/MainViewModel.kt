package com.example.catalogdemo.ui.main

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.catalogdemo.data.mapper.toProductList
import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.data.repository.ProductPagingSource
import com.example.catalogdemo.domain.model.ProductItem
import com.example.catalogdemo.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: List<ProductItem>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductListVewModel @Inject constructor(
    private val apiService: ApiService,
    private val repository: Repository,
) : ViewModel(){

    private val allProductPageFlow: Flow<PagingData<ProductItem>> =
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

    val searchProductPageFlow: Flow<PagingData<ProductItem>> =
        snapshotFlow { textFieldState.text.toString() }
            .debounce(300L)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isEmpty()){
                    allProductPageFlow
                }else{
                    flow {
                        val res = apiService.searchProduct(query)
                        val searchResults = res.products.map { it.toProductList() }
                        emit(PagingData.from(searchResults))
                    }
                }
            }
            .cachedIn(viewModelScope)


    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _searchState.value = SearchUiState.Loading
            try {
                val results = repository.searchProduct(query)
                _searchState.value = SearchUiState.Success(results)
            } catch (e: Exception) {
                _searchState.value = SearchUiState.Error(e.localizedMessage ?: "Error searching")
            }
        }
    }

}