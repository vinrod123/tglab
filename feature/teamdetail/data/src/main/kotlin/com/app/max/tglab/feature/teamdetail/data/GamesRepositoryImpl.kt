package com.app.max.tglab.feature.teamdetail.data

import com.app.max.tglab.core.common.DispatcherProvider
import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.feature.teamdetail.domain.Game
import com.app.max.tglab.feature.teamdetail.domain.GamesRepository
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GamesRepositoryImpl
    @Inject
    constructor(
        private val gamesApi: GamesApi,
        private val dispatcherProvider: DispatcherProvider,
    ) : GamesRepository {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> =
            withContext(dispatcherProvider.io) {
                try {
                    DomainResult.Success(gamesApi.getGames(listOf(teamId)).data.mapNotNull { it.toDomain() })
                } catch (exception: IOException) {
                    DomainResult.Error(DomainError.NetworkUnavailable)
                } catch (exception: HttpException) {
                    DomainResult.Error(DomainError.Http(exception.code()))
                } catch (exception: Exception) {
                    DomainResult.Error(DomainError.Unknown)
                }
            }
    }
