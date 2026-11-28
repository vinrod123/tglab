package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface HomeUiIntent : MviViewModel.UiIntent {
    data object LoadTeams : HomeUiIntent
}
