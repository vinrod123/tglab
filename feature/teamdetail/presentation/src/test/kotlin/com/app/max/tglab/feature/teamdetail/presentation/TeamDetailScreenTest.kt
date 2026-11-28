package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class TeamDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val oneGame =
        listOf(
            GameUiModel(
                id = 42,
                homeTeamName = "Atlanta Hawks",
                homeTeamScore = 102,
                visitorTeamName = "Boston Celtics",
                visitorTeamScore = 98,
            ),
        )

    @Test
    fun `should not display the numeric teamId as text when state is Content (AC-1)`() {
        composeTestRule.setContent {
            TeamDetailScreen(teamName = "Boston Celtics", state = TeamDetailUiState.Content(games = oneGame), onIntent = {})
        }

        composeTestRule.onNodeWithText("42").assertDoesNotExist()
    }

    @Test
    fun `should display homeTeamName, homeTeamScore, visitorTeamName and visitorTeamScore for each game in the order received when state is Content (AC-2)`() {
        val games =
            listOf(
                GameUiModel(
                    id = 1,
                    homeTeamName = "Atlanta Hawks",
                    homeTeamScore = 102,
                    visitorTeamName = "Boston Celtics",
                    visitorTeamScore = 98,
                ),
                GameUiModel(
                    id = 2,
                    homeTeamName = "Golden State Warriors",
                    homeTeamScore = 110,
                    visitorTeamName = "LA Lakers",
                    visitorTeamScore = 115,
                ),
            )

        composeTestRule.setContent {
            TeamDetailScreen(teamName = "Miami Heat", state = TeamDetailUiState.Content(games = games), onIntent = {})
        }

        composeTestRule.onNodeWithTag("team_detail_games_list").assertExists()
        composeTestRule.onNodeWithText("Atlanta Hawks", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("102", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Boston Celtics", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("98", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Golden State Warriors", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("110", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("LA Lakers", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("115", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `should invoke onIntent with NavigateBack when the back Home button is clicked (AC-3)`() {
        var receivedIntent: TeamDetailUiIntent? = null

        composeTestRule.setContent {
            TeamDetailScreen(
                teamName = "Boston Celtics",
                state = TeamDetailUiState.Content(games = oneGame),
                onIntent = { receivedIntent = it },
            )
        }
        composeTestRule.onNodeWithTag("team_detail_back_button").performClick()

        assertThat(receivedIntent).isEqualTo(TeamDetailUiIntent.NavigateBack)
    }

    @Test
    fun `should show the empty message and hide list, error and loading content when state is Empty (AC-4)`() {
        composeTestRule.setContent {
            TeamDetailScreen(teamName = "Boston Celtics", state = TeamDetailUiState.Empty, onIntent = {})
        }

        composeTestRule.onNodeWithTag("team_detail_empty_state").assertExists()
        composeTestRule.onNodeWithTag("team_detail_games_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("team_detail_error_state").assertDoesNotExist()
        composeTestRule.onNodeWithTag("team_detail_loading_state").assertDoesNotExist()
    }

    @Test
    fun `should show the loading indicator and hide list, error and empty content when state is Loading (AC-5)`() {
        composeTestRule.setContent {
            TeamDetailScreen(teamName = "Boston Celtics", state = TeamDetailUiState.Loading, onIntent = {})
        }

        composeTestRule.onNodeWithTag("team_detail_loading_state").assertExists()
        composeTestRule.onNodeWithTag("team_detail_games_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("team_detail_error_state").assertDoesNotExist()
        composeTestRule.onNodeWithTag("team_detail_empty_state").assertDoesNotExist()
    }

    @Test
    fun `should show the offline-specific message when state is Error with reason NETWORK_UNAVAILABLE (AC-6)`() {
        composeTestRule.setContent {
            TeamDetailScreen(
                teamName = "Boston Celtics",
                state = TeamDetailUiState.Error(reason = TeamDetailErrorReason.NETWORK_UNAVAILABLE),
                onIntent = {},
            )
        }

        composeTestRule.onNodeWithTag("team_detail_error_state").assertExists()
        composeTestRule.onNodeWithText("Sem conexão. Verifique sua internet e tente novamente.").assertExists()
    }

    @Test
    fun `should show the generic error message and retry button when state is Error with reason SERVER (AC-6)`() {
        composeTestRule.setContent {
            TeamDetailScreen(
                teamName = "Boston Celtics",
                state = TeamDetailUiState.Error(reason = TeamDetailErrorReason.SERVER),
                onIntent = {},
            )
        }

        composeTestRule.onNodeWithTag("team_detail_error_state").assertExists()
        composeTestRule.onNodeWithText("Não foi possível carregar os jogos").assertExists()
        composeTestRule.onNodeWithTag("team_detail_error_retry_button").assertExists()
    }

    @Test
    fun `should show the generic error message and retry button when state is Error with reason UNKNOWN (AC-6)`() {
        composeTestRule.setContent {
            TeamDetailScreen(
                teamName = "Boston Celtics",
                state = TeamDetailUiState.Error(reason = TeamDetailErrorReason.UNKNOWN),
                onIntent = {},
            )
        }

        composeTestRule.onNodeWithTag("team_detail_error_state").assertExists()
        composeTestRule.onNodeWithText("Não foi possível carregar os jogos").assertExists()
        composeTestRule.onNodeWithTag("team_detail_error_retry_button").assertExists()
    }

    @Test
    fun `should invoke onIntent with LoadGames when the retry button is clicked (AC-6)`() {
        var receivedIntent: TeamDetailUiIntent? = null

        composeTestRule.setContent {
            TeamDetailScreen(
                teamName = "Boston Celtics",
                state = TeamDetailUiState.Error(reason = TeamDetailErrorReason.SERVER),
                onIntent = { receivedIntent = it },
            )
        }
        composeTestRule.onNodeWithTag("team_detail_error_retry_button").performClick()

        assertThat(receivedIntent).isEqualTo(TeamDetailUiIntent.LoadGames)
    }
}
