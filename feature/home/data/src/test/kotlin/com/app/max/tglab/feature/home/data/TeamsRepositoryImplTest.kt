package com.app.max.tglab.feature.home.data

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

class TeamsRepositoryImplTest {
    @Test
    fun `should return mapped teams when api call succeeds (AC-1)`() =
        runTest {
            val fakeTeamsApi =
                FakeTeamsApi(
                    response =
                        TeamsResponseDto(
                            data =
                                listOf(
                                    TeamDto(id = 1, fullName = "Atlanta Hawks", city = "Atlanta", conference = "East"),
                                ),
                        ),
                )
            val teamsRepositoryImpl = TeamsRepositoryImpl(fakeTeamsApi, TestDispatcherProvider())

            val result = teamsRepositoryImpl.getTeams()

            val success = result as DomainResult.Success
            assertThat(success.data.single().fullName).isEqualTo("Atlanta Hawks")
        }

    @Test
    fun `should return DomainError Http when the api call throws HttpException (AC-3)`() =
        runTest {
            val httpException = HttpException(Response.error<Any>(500, "".toResponseBody(null)))
            val fakeTeamsApi = FakeTeamsApi(exceptionToThrow = httpException)
            val teamsRepositoryImpl = TeamsRepositoryImpl(fakeTeamsApi, TestDispatcherProvider())

            val result = teamsRepositoryImpl.getTeams()

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.Http(500)))
        }

    @Test
    fun `should return DomainError Unknown when the api call throws an unexpected exception (AC-3)`() =
        runTest {
            val fakeTeamsApi = FakeTeamsApi(exceptionToThrow = IllegalStateException("boom"))
            val teamsRepositoryImpl = TeamsRepositoryImpl(fakeTeamsApi, TestDispatcherProvider())

            val result = teamsRepositoryImpl.getTeams()

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.Unknown))
        }

    @Test
    fun `should return DomainError NetworkUnavailable when the api call throws IOException (AC-5)`() =
        runTest {
            val fakeTeamsApi = FakeTeamsApi(exceptionToThrow = IOException("no connection"))
            val teamsRepositoryImpl = TeamsRepositoryImpl(fakeTeamsApi, TestDispatcherProvider())

            val result = teamsRepositoryImpl.getTeams()

            assertThat(result).isEqualTo(DomainResult.Error(DomainError.NetworkUnavailable))
        }
}
