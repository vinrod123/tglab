package com.app.max.tglab.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], application = android.app.Application::class)
class TgLabNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `should display the app name placeholder text when rendered before any feature route is wired in`() {
        composeTestRule.setContent {
            TgLabNavHost()
        }

        composeTestRule.onNodeWithText("TgLab").assertIsDisplayed()
    }
}
