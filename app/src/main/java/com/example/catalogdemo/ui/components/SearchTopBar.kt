package com.example.catalogdemo.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalogdemo.R

@ExperimentalMaterial3Api
@Composable
fun SearchTopBar(){
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { } // TODO: Future navigation
            ) {
                Icon(painter = painterResource(R.drawable.baseline_menu_24),
                    contentDescription = "Menu")
            }
        },
        title = {
            TextField(
                state = rememberTextFieldState(),
                placeholder = { Text("Search") },
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ){
                        Icon(painter = painterResource(R.drawable.baseline_search_24),
                            contentDescription = "Search button")
                    }
                },
                shape = RoundedCornerShape(8.dp)
            )
        },
        actions = {
            IconButton(
                onClick = { } // TODO: View cart
            ) {
                Icon(painter = painterResource(R.drawable.outline_add_shopping_cart_24),
                    contentDescription = "Cart")
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewSearch(){
    SearchTopBar()
}