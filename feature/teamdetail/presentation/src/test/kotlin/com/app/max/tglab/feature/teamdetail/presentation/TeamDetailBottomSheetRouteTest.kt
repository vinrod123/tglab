package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.testing.MainDispatcherRule
import com.app.max.tglab.feature.teamdetail.domain.Game
import com.app.max.tglab.feature.teamdetail.domain.GamesRepository
import com.app.max.tglab.feature.teamdetail.domain.GetTeamGamesUseCase
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class TeamDetailBottomSheetRouteTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeGamesRepository : GamesRepository {
        var result: DomainResult<List<Game>> = DomainResult.Success(emptyList())

        override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> = result
    }

    @Test
    fun `should invoke onNavigateBack when TeamDetailViewModel emits NavigateBack (AC-3)`() {
        val fakeGamesRepository = FakeGamesRepository()
        val savedStateHandle = SavedStateHandle(mapOf("teamId" to 1L, "teamName" to "Atlanta Hawks"))
        val teamDetailViewModel = TeamDetailViewModel(savedStateHandle, GetTeamGamesUseCase(fakeGamesRepository))
        var onNavigateBackCallCount = 0

        composeTestRule.setContent {
            TeamDetailBottomSheetRoute(
                onNavigateBack = { onNavigateBackCallCount++ },
                teamDetailViewModel = teamDetailViewModel,
            )
        }
        composeTestRule.waitForIdle()

        teamDetailViewModel.onIntent(TeamDetailUiIntent.NavigateBack)
        composeTestRule.waitForIdle()

        assertThat(onNavigateBackCallCount).isEqualTo(1)
    }
}
