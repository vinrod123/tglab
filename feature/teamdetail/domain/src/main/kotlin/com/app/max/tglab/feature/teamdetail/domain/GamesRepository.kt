package com.app.max.tglab.feature.teamdetail.domain

import com.app.max.tglab.core.common.DomainResult

interface GamesRepository {
    suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>>
}
