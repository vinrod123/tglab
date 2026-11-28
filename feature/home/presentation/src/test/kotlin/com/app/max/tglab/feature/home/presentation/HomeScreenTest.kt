package com.app.max.tglab.feature.home.presentation

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
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `should display fullName, city and conference for each team when state is Content (AC-1)`() {
        val teams =
            listOf(
                TeamUiModel(id = 1, fullName = "Atlanta Hawks", city = "Atlanta", conference = "East"),
            )

        composeTestRule.setContent {
            HomeScreen(state = HomeUiState.Content(teams = teams), onIntent = {})
        }

        composeTestRule.onNodeWithTag("home_teams_list").assertExists()
        composeTestRule.onNodeWithText("Atlanta Hawks").assertExists()
        composeTestRule.onNodeWithText("Atlanta").assertExists()
        composeTestRule.onNodeWithText("East").assertExists()
    }

    @Test
    fun `should show the loading indicator and hide list, error and empty content when state is Loading (AC-2)`() {
        composeTestRule.setContent {
            HomeScreen(state = HomeUiState.Loading, onIntent = {})
        }

        composeTestRule.onNodeWithTag("home_loading_state").assertExists()
        composeTestRule.onNodeWithTag("home_teams_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("home_error_state").assertDoesNotExist()
        composeTestRule.onNodeWithTag("home_empty_state").assertDoesNotExist()
    }

    @Test
    fun `should show the generic error title, message and retry button when state is Error with reason SERVER (AC-3)`() {
        composeTestRule.setContent {
            HomeScreen(state = HomeUiState.Error(reason = HomeErrorReason.SERVER), onIntent = {})
        }

        composeTestRule.onNodeWithTag("home_error_state").assertExists()
        composeTestRule.onNodeWithText("Não foi possível carregar os times").assertExists()
        composeTestRule.onNodeWithTag("home_error_retry_button").assertExists()
    }

    @Test
    fun `should invoke onIntent with LoadTeams when the retry button is clicked (AC-3)`() {
        var receivedIntent: HomeUiIntent? = null

        composeTestRule.setContent {
            HomeScreen(
                state = HomeUiState.Error(reason = HomeErrorReason.SERVER),
                onIntent = { receivedIntent = it },
            )
        }
        composeTestRule.onNodeWithTag("home_error_retry_button").performClick()

        assertThat(receivedIntent).isEqualTo(HomeUiIntent.LoadTeams)
    }

    @Test
    fun `should show the empty message and hide list, error and loading content when state is Empty (AC-4)`() {
        composeTestRule.setContent {
            HomeScreen(state = HomeUiState.Empty, onIntent = {})
        }

        composeTestRule.onNodeWithTag("home_empty_state").assertExists()
        composeTestRule.onNodeWithTag("home_teams_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("home_error_state").assertDoesNotExist()
        composeTestRule.onNodeWithTag("home_loading_state").assertDoesNotExist()
    }

    @Test
    fun `should show an offline-specific title and message, distinct from the generic error, when state is Error with reason NETWORK_UNAVAILABLE (AC-5)`() {
        composeTestRule.setContent {
            HomeScreen(state = HomeUiState.Error(reason = HomeErrorReason.NETWORK_UNAVAILABLE), onIntent = {})
        }

        composeTestRule.onNodeWithTag("home_error_state").assertExists()
        composeTestRule.onNodeWithText("Sem conexão. Verifique sua internet e tente novamente.").assertExists()
    }
}
