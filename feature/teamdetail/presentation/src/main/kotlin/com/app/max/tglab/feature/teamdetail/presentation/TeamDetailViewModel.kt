package com.app.max.tglab.feature.teamdetail.presentation

import androidx.lifecycle.SavedStateHandle
import com.app.max.tglab.core.ui.MviViewModel
import com.app.max.tglab.feature.teamdetail.domain.GetTeamGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val getTeamGamesUseCase: GetTeamGamesUseCase,
    ) : MviViewModel<TeamDetailUiState, TeamDetailUiIntent, TeamDetailUiEffect>(initialState = TeamDetailUiState.Loading) {
        val teamName: String
            get() = TODO()

        override suspend fun handleIntent(intent: TeamDetailUiIntent) {
            when (intent) {
                is TeamDetailUiIntent.LoadGames -> TODO()
                is TeamDetailUiIntent.NavigateBack -> TODO()
            }
        }
    }
