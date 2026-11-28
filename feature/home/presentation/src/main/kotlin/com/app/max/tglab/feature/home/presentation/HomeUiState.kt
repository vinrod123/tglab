package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface HomeUiState : MviViewModel.UiState {
    data object Loading : HomeUiState

    data class Content(
        val teams: List<TeamUiModel>,
    ) : HomeUiState

    data object Empty : HomeUiState

    data class Error(
        val reason: HomeErrorReason,
    ) : HomeUiState
}
