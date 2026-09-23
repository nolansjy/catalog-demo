package com.example.catalogdemo.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.catalogdemo.ui.components.SearchTopBar

@ExperimentalMaterial3Api
@Composable
fun MainScreen(){
    Scaffold(
        topBar = {
            SearchTopBar()
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)){

        }
    }
}