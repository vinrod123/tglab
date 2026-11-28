package com.app.max.tglab.feature.teamdetail.domain

import com.app.max.tglab.core.common.DomainResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetTeamGamesUseCaseTest {
    @Test
    fun `should return games sorted by date descending when repository succeeds with an unsorted list (AC-2)`() =
        runTest {
            val unsortedGames =
                listOf(
                    Game(
                        id = 1,
                        date = "2026-01-01",
                        homeTeamName = "Atlanta Hawks",
                        homeTeamScore = 100,
                        visitorTeamName = "Boston Celtics",
                        visitorTeamScore = 98,
                    ),
                    Game(
                        id = 2,
                        date = "2026-03-01",
                        homeTeamName = "Golden State Warriors",
                        homeTeamScore = 110,
                        visitorTeamName = "LA Lakers",
                        visitorTeamScore = 115,
                    ),
                )
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Success(unsortedGames)
                }
            val getTeamGamesUseCase = GetTeamGamesUseCase(fakeGamesRepository)

            val result = getTeamGamesUseCase(teamId = 1)

            val sortedResult = result as DomainResult.Success
            assertThat(sortedResult.data.map { it.date })
                .containsExactly("2026-03-01", "2026-01-01")
                .inOrder()
        }

    @Test
    fun `should return an empty list unchanged when repository succeeds with an empty list (AC-4)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Success(emptyList())
                }
            val getTeamGamesUseCase = GetTeamGamesUseCase(fakeGamesRepository)

            val result = getTeamGamesUseCase(teamId = 1)

            val emptyResult = result as DomainResult.Success
            assertThat(emptyResult.data).isEmpty()
        }
}
