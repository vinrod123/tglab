package com.app.max.tglab.feature.home.domain

import com.app.max.tglab.core.common.DomainResult

class GetTeamsUseCase(
    private val teamsRepository: TeamsRepository,
) {
    suspend operator fun invoke(): DomainResult<List<Team>> = TODO()
}
