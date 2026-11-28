package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface HomeUiEffect : MviViewModel.UiEffect {
    data class NavigateToTeamDetail(
        val teamId: Long,
        val teamName: String,
    ) : HomeUiEffect
}
