package com.example.catalogdemo.ui.main

import android.util.Log
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



@HiltViewModel
class ProductListVewModel @Inject constructor(
    private val apiService: ApiService,
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


}