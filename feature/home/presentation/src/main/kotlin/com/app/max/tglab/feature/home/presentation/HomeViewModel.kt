package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.ui.MviViewModel
import com.app.max.tglab.feature.home.domain.GetTeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getTeamsUseCase: GetTeamsUseCase,
    ) : MviViewModel<HomeUiState, HomeUiIntent, HomeUiEffect>(initialState = HomeUiState.Loading) {
        override suspend fun handleIntent(intent: HomeUiIntent) {
            when (intent) {
                is HomeUiIntent.LoadTeams -> loadTeams()
            }
        }

        private suspend fun loadTeams() {
            updateState { HomeUiState.Loading }
            when (val result = getTeamsUseCase()) {
                is DomainResult.Success ->
                    updateState {
                        if (result.data.isEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.Content(result.data.map { it.toUiModel() })
                        }
                    }
                is DomainResult.Error -> updateState { HomeUiState.Error(result.error.toHomeErrorReason()) }
            }
        }
    }
