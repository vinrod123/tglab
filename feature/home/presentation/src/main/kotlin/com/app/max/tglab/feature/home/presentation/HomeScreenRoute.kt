package com.app.max.tglab.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreenRoute(
    onNavigateToTeamDetail: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeUiIntent.LoadTeams)
    }

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToTeamDetail -> onNavigateToTeamDetail(effect.teamId, effect.teamName)
            }
        }
    }

    HomeScreen(
        state = state,
        onIntent = homeViewModel::onIntent,
        modifier = modifier,
    )
}
