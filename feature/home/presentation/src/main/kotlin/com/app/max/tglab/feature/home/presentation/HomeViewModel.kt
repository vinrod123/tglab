package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.ui.MviViewModel
import com.app.max.tglab.feature.home.domain.GetTeamsUseCase

class HomeViewModel(
    private val getTeamsUseCase: GetTeamsUseCase,
) : MviViewModel<HomeUiState, HomeUiIntent, HomeUiEffect>(initialState = HomeUiState.Loading) {
    override suspend fun handleIntent(intent: HomeUiIntent) {
        TODO()
    }
}
