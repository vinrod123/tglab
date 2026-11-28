package com.app.max.tglab.feature.home.domain

import com.app.max.tglab.core.common.DomainResult

interface TeamsRepository {
    suspend fun getTeams(): DomainResult<List<Team>>
}
