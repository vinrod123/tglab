package com.app.max.tglab.feature.teamdetail.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface TeamDetailUiIntent : MviViewModel.UiIntent {
    data object LoadGames : TeamDetailUiIntent

    data object NavigateBack : TeamDetailUiIntent
}
