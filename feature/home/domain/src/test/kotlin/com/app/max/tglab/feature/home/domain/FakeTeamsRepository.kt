package com.app.max.tglab.feature.home.domain

import com.app.max.tglab.core.common.DomainResult

class FakeTeamsRepository : TeamsRepository {
    var result: DomainResult<List<Team>> = DomainResult.Success(emptyList())

    override suspend fun getTeams(): DomainResult<List<Team>> = result
}
