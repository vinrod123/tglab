package com.app.max.tglab.feature.teamdetail.domain

import com.app.max.tglab.core.common.DomainResult

class GetTeamGamesUseCase(
    private val gamesRepository: GamesRepository,
) {
    suspend operator fun invoke(teamId: Long): DomainResult<List<Game>> = TODO()
}
