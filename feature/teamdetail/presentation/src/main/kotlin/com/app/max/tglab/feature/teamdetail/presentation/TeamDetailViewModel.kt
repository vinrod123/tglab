package com.app.max.tglab.feature.teamdetail.presentation

import androidx.lifecycle.SavedStateHandle
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.ui.MviViewModel
import com.app.max.tglab.feature.teamdetail.domain.GetTeamGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TEAM_ID_SAVED_STATE_KEY = "teamId"
private const val TEAM_NAME_SAVED_STATE_KEY = "teamName"

@HiltViewModel
class TeamDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getTeamGamesUseCase: GetTeamGamesUseCase,
    ) : MviViewModel<TeamDetailUiState, TeamDetailUiIntent, TeamDetailUiEffect>(initialState = TeamDetailUiState.Loading) {
        private val teamId: Long = checkNotNull(savedStateHandle.get<Long>(TEAM_ID_SAVED_STATE_KEY))
        val teamName: String = checkNotNull(savedStateHandle.get<String>(TEAM_NAME_SAVED_STATE_KEY))

        override suspend fun handleIntent(intent: TeamDetailUiIntent) {
            when (intent) {
                is TeamDetailUiIntent.LoadGames -> loadGames()
                is TeamDetailUiIntent.NavigateBack -> sendEffect(TeamDetailUiEffect.NavigateBack)
            }
        }

        private suspend fun loadGames() {
            updateState { TeamDetailUiState.Loading }
            when (val result = getTeamGamesUseCase(teamId)) {
                is DomainResult.Success ->
                    updateState {
                        if (result.data.isEmpty()) {
                            TeamDetailUiState.Empty
                        } else {
                            TeamDetailUiState.Content(result.data.map { it.toUiModel() })
                        }
                    }
                is DomainResult.Error -> updateState { TeamDetailUiState.Error(result.error.toTeamDetailErrorReason()) }
            }
        }
    }
