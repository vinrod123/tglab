package com.app.max.tglab.feature.teamdetail.data

import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.testing.TestDispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GamesRepositoryImplTest {
    @Test
    fun `should return only mapped completed games when api call succeeds with a mix of finished and future games (AC-2)`() =
        runTest {
            val fakeGamesApi =
                FakeGamesApi(
                    response =
                        GamesResponseDto(
                            data =
                                listOf(
                                    GameDto(
                                        id = 1,
                                        date = "2026-03-01",
                                        homeTeam = GameTeamDto(fullName = "Atlanta Hawks"),
                                        homeTeamScore = 102,
                                        visitorTeam = GameTeamDto(fullName = "Boston Celtics"),
                                        visitorTeamScore = 98,
                                    ),
                                    GameDto(
                                        id = 2,
                                        date = "2026-04-01",
                                        homeTeam = GameTeamDto(fullName = "LA Lakers"),
                                        homeTeamScore = null,
                                        visitorTeam = GameTeamDto(fullName = "Golden State Warriors"),
                                        visitorTeamScore = null,
                                    ),
                                ),
                        ),
                )
            val gamesRepositoryImpl = GamesRepositoryImpl(fakeGamesApi, TestDispatcherProvider())

            val result = gamesRepositoryImpl.getTeamGames(teamId = 1)

            val success = result as DomainResult.Success
            assertThat(success.data.map { it.id }).containsExactly(1L)
        }

    @Test
    fun `should return DomainError Http when the api call throws HttpException (AC-6)`() =
        runTest {
            val httpException = HttpException(Response.error<Any>(500, "".toResponseBody(null)))
            val fakeGamesApi = FakeGamesApi(exceptionToThrow = httpException)
            val gamesRepositoryImpl = GamesRepositoryImpl(fakeGamesApi, TestDispatcherProvider())

            val result = gamesRepositoryImpl.getTeamGames(teamId = 1)

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.Http(500)))
        }

    @Test
    fun `should return DomainError Unknown when the api call throws an unexpected exception (AC-6)`() =
        runTest {
            val fakeGamesApi = FakeGamesApi(exceptionToThrow = IllegalStateException("boom"))
            val gamesRepositoryImpl = GamesRepositoryImpl(fakeGamesApi, TestDispatcherProvider())

            val result = gamesRepositoryImpl.getTeamGames(teamId = 1)

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.Unknown))
        }

    @Test
    fun `should return DomainError NetworkUnavailable when the api call throws IOException (AC-6)`() =
        runTest {
            val fakeGamesApi = FakeGamesApi(exceptionToThrow = IOException("no connection"))
            val gamesRepositoryImpl = GamesRepositoryImpl(fakeGamesApi, TestDispatcherProvider())

            val result = gamesRepositoryImpl.getTeamGames(teamId = 1)

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.NetworkUnavailable))
        }
}
