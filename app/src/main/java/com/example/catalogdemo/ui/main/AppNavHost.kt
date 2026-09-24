package com.example.catalogdemo.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@ExperimentalMaterial3Api
@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Main){
        composable<Main>{
            MainScreen(
                navToDetail = { productId ->
                    navController.navigate(Detail(id = productId)) }
            )
        }

        composable<Detail>{
            ProductScreen(
                onNavBack = { navController.popBackStack() }
            )
        }
    }
}

@Serializable data object Main
@Serializable data class Detail(val id: Int)