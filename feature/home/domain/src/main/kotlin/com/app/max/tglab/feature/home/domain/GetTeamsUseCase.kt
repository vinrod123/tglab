package com.app.max.tglab.feature.home.domain

import com.app.max.tglab.core.common.DomainResult

class GetTeamsUseCase(
    private val teamsRepository: TeamsRepository,
) {
    suspend operator fun invoke(): DomainResult<List<Team>> =
        when (val result = teamsRepository.getTeams()) {
            is DomainResult.Success -> DomainResult.Success(result.data.sortedBy { it.fullName })
            is DomainResult.Error -> result
        }
}
