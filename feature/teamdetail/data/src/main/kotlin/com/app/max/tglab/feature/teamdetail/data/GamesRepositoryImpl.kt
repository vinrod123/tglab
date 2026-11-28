package com.app.max.tglab.feature.teamdetail.data

import com.app.max.tglab.core.common.DispatcherProvider
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.feature.teamdetail.domain.Game
import com.app.max.tglab.feature.teamdetail.domain.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl
    @Inject
    constructor(
        private val gamesApi: GamesApi,
        private val dispatcherProvider: DispatcherProvider,
    ) : GamesRepository {
        override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> = TODO()
    }
