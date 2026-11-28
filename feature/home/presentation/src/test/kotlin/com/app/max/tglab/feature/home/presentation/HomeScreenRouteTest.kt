package com.app.max.tglab.feature.home.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.testing.MainDispatcherRule
import com.app.max.tglab.feature.home.domain.GetTeamsUseCase
import com.app.max.tglab.feature.home.domain.Team
import com.app.max.tglab.feature.home.domain.TeamsRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HomeScreenRouteTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeTeamsRepository : TeamsRepository {
        var result: DomainResult<List<Team>> = DomainResult.Success(emptyList())
        var getTeamsCallCount = 0

        override suspend fun getTeams(): DomainResult<List<Team>> {
            getTeamsCallCount++
            return result
        }
    }

    @Test
    fun `should dispatch LoadTeams intent on first composition`() {
        val fakeTeamsRepository = FakeTeamsRepository()
        val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

        composeTestRule.setContent {
            HomeScreenRoute(homeViewModel = homeViewModel, onNavigateToTeamDetail = { _, _ -> })
        }
        composeTestRule.waitForIdle()

        assertThat(fakeTeamsRepository.getTeamsCallCount).isEqualTo(1)
    }

    @Test
    fun `should invoke onNavigateToTeamDetail with teamId and teamName when HomeViewModel emits NavigateToTeamDetail (AC-1)`() {
        val fakeTeamsRepository = FakeTeamsRepository()
        val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))
        var receivedTeamId: Long? = null
        var receivedTeamName: String? = null

        composeTestRule.setContent {
            HomeScreenRoute(
                homeViewModel = homeViewModel,
                onNavigateToTeamDetail = { teamId, teamName ->
                    receivedTeamId = teamId
                    receivedTeamName = teamName
                },
            )
        }
        composeTestRule.waitForIdle()

        homeViewModel.onIntent(HomeUiIntent.TeamTapped(teamId = 1, teamName = "Atlanta Hawks"))
        composeTestRule.waitForIdle()

        assertThat(receivedTeamId).isEqualTo(1L)
        assertThat(receivedTeamName).isEqualTo("Atlanta Hawks")
    }
}
