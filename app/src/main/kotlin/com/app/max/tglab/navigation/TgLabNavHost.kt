package com.app.max.tglab.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.max.tglab.R

private const val ROOT_PLACEHOLDER_ROUTE = "root_placeholder"

@Composable
fun TgLabNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ROOT_PLACEHOLDER_ROUTE) {
        composable(ROOT_PLACEHOLDER_ROUTE) {
            RootPlaceholderScreen()
        }
    }
}

@Composable
private fun RootPlaceholderScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(R.string.app_name))
        }
    }
}
