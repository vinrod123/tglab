package com.app.max.tglab.feature.teamdetail.domain

import com.app.max.tglab.core.common.DomainResult

class GetTeamGamesUseCase(
    private val gamesRepository: GamesRepository,
) {
    suspend operator fun invoke(teamId: Long): DomainResult<List<Game>> =
        when (val result = gamesRepository.getTeamGames(teamId)) {
            is DomainResult.Success -> DomainResult.Success(result.data.sortedByDescending { it.date })
            is DomainResult.Error -> result
        }
}
