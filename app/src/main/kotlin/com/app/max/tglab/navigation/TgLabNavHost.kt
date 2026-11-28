package com.app.max.tglab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.max.tglab.feature.home.presentation.HomeRoute
import com.app.max.tglab.feature.home.presentation.HomeScreenRoute

@Composable
fun TgLabNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreenRoute()
        }
    }
}
