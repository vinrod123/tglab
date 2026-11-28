package com.app.max.tglab.feature.home.data

import com.app.max.tglab.core.common.DispatcherProvider
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.feature.home.domain.Team
import com.app.max.tglab.feature.home.domain.TeamsRepository

class TeamsRepositoryImpl(
    private val teamsApi: TeamsApi,
    private val dispatcherProvider: DispatcherProvider,
) : TeamsRepository {
    override suspend fun getTeams(): DomainResult<List<Team>> = TODO()
}
