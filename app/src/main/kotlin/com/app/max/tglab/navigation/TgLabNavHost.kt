package com.app.max.tglab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.max.tglab.feature.home.presentation.HomeRoute
import com.app.max.tglab.feature.home.presentation.HomeScreenRoute
import com.app.max.tglab.feature.teamdetail.presentation.TeamDetailBottomSheetRoute
import com.app.max.tglab.feature.teamdetail.presentation.TeamDetailRoute

@Composable
fun TgLabNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreenRoute(
                onNavigateToTeamDetail = { teamId, teamName ->
                    navController.navigate(TeamDetailRoute(teamId = teamId, teamName = teamName))
                },
            )
        }
        composable<TeamDetailRoute> {
            TeamDetailBottomSheetRoute(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
