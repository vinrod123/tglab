package com.app.max.tglab.feature.home.data

import com.app.max.tglab.core.common.DispatcherProvider
import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.feature.home.domain.Team
import com.app.max.tglab.feature.home.domain.TeamsRepository
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TeamsRepositoryImpl
    @Inject
    constructor(
        private val teamsApi: TeamsApi,
        private val dispatcherProvider: DispatcherProvider,
    ) : TeamsRepository {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        override suspend fun getTeams(): DomainResult<List<Team>> =
            withContext(dispatcherProvider.io) {
                try {
                    DomainResult.Success(teamsApi.getTeams().data.map { it.toDomain() })
                } catch (exception: IOException) {
                    DomainResult.Error(DomainError.NetworkUnavailable)
                } catch (exception: HttpException) {
                    DomainResult.Error(DomainError.Http(exception.code()))
                } catch (exception: Exception) {
                    DomainResult.Error(DomainError.Unknown)
                }
            }
    }
