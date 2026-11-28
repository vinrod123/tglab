package com.app.max.tglab.feature.teamdetail.domain

import com.app.max.tglab.core.common.DomainResult

class FakeGamesRepository : GamesRepository {
    var result: DomainResult<List<Game>> = DomainResult.Success(emptyList())

    override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> = result
}
