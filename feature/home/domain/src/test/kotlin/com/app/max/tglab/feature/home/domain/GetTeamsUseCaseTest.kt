package com.app.max.tglab.feature.home.domain

import com.app.max.tglab.core.common.DomainResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetTeamsUseCaseTest {
    @Test
    fun `should return teams sorted by full name when repository succeeds with an unsorted list (AC-1)`() =
        runTest {
            val unsortedTeams =
                listOf(
                    Team(id = 2, fullName = "Boston Celtics", city = "Boston", conference = "East"),
                    Team(id = 1, fullName = "Atlanta Hawks", city = "Atlanta", conference = "East"),
                )
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Success(unsortedTeams)
                }
            val getTeamsUseCase = GetTeamsUseCase(fakeTeamsRepository)

            val result = getTeamsUseCase()

            val sortedResult = result as DomainResult.Success
            assertThat(sortedResult.data.map { it.fullName })
                .containsExactly("Atlanta Hawks", "Boston Celtics")
                .inOrder()
        }

    @Test
    fun `should return an empty list unchanged when repository succeeds with an empty list (AC-4)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Success(emptyList())
                }
            val getTeamsUseCase = GetTeamsUseCase(fakeTeamsRepository)

            val result = getTeamsUseCase()

            val emptyResult = result as DomainResult.Success
            assertThat(emptyResult.data).isEmpty()
        }
}
