package com.example.catalogdemo.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.catalogdemo.ui.components.ProductCard
import com.example.catalogdemo.ui.components.SearchTopBar

@ExperimentalMaterial3Api
@Composable
fun MainScreen(navToDetail: (Int) -> Unit){
    val viewModel: ProductListVewModel = hiltViewModel()
    val productList = viewModel.productPageFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar()
        },
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            items(
                count = productList.itemCount,
                key = productList.itemKey { it.id }
            ) { index ->
                val product = productList[index]
                if (product != null) {
                    ProductCard(item = product, navToDetail = { navToDetail(product.id) })
                }
            }

            when (val appendState = productList.loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            TextButton(onClick = { productList.retry() }) {
                                Text("Failed to load more. Tap to retry.")
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}