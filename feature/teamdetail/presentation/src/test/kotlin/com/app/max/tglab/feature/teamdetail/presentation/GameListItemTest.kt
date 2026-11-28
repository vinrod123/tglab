package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class GameListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `should expose a single combined semantics node with homeTeamName, homeTeamScore, visitorTeamName and visitorTeamScore (AC-2)`() {
        val game =
            GameUiModel(
                id = 42,
                homeTeamName = "Atlanta Hawks",
                homeTeamScore = 102,
                visitorTeamName = "Boston Celtics",
                visitorTeamScore = 98,
            )

        composeTestRule.setContent {
            GameListItem(game = game)
        }

        composeTestRule
            .onNodeWithContentDescription("Atlanta Hawks 102 a Boston Celtics 98")
            .assertExists()
    }
}
