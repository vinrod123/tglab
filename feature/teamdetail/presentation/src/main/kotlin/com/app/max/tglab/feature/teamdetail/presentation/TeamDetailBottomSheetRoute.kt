package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TeamDetailBottomSheetRoute(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    teamDetailViewModel: TeamDetailViewModel = hiltViewModel(),
) {
    val state by teamDetailViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)
    }

    LaunchedEffect(Unit) {
        teamDetailViewModel.effect.collect { effect ->
            when (effect) {
                is TeamDetailUiEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    TeamDetailScreen(
        teamName = teamDetailViewModel.teamName,
        state = state,
        onIntent = teamDetailViewModel::onIntent,
        modifier = modifier,
    )
}
