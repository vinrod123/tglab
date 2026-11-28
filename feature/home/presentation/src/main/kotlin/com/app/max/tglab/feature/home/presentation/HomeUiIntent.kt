package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface HomeUiIntent : MviViewModel.UiIntent {
    data object LoadTeams : HomeUiIntent

    data class TeamTapped(
        val teamId: Long,
        val teamName: String,
    ) : HomeUiIntent
}
