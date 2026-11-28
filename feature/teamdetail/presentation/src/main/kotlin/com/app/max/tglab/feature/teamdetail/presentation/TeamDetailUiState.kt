package com.app.max.tglab.feature.teamdetail.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface TeamDetailUiState : MviViewModel.UiState {
    data object Loading : TeamDetailUiState

    data class Content(
        val games: List<GameUiModel>,
    ) : TeamDetailUiState

    data object Empty : TeamDetailUiState

    data class Error(
        val reason: TeamDetailErrorReason,
    ) : TeamDetailUiState
}
