package com.app.max.tglab.feature.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.max.tglab.core.designsystem.theme.TgLabTheme
import com.app.max.tglab.core.ui.EmptyState
import com.app.max.tglab.core.ui.ErrorState
import com.app.max.tglab.core.ui.LoadingState

private const val HOME_ERROR_RETRY_BUTTON_TEST_TAG = "home_error_retry_button"

@Composable
fun HomeScreen(
    state: HomeUiState,
    onIntent: (HomeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.testTag("home_screen"),
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.home_title)) }) },
    ) { contentPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
            when (state) {
                is HomeUiState.Loading -> HomeLoadingContent()
                is HomeUiState.Content -> HomeTeamsList(teams = state.teams)
                is HomeUiState.Empty -> HomeEmptyContent()
                is HomeUiState.Error -> HomeErrorContent(reason = state.reason, onIntent = onIntent)
            }
        }
    }
}

@Composable
private fun HomeLoadingContent() {
    LoadingState(
        message = stringResource(R.string.home_loading_message),
        modifier = Modifier.testTag("home_loading_state"),
    )
}

@Composable
private fun HomeTeamsList(teams: List<TeamUiModel>) {
    LazyColumn(modifier = Modifier.testTag("home_teams_list")) {
        items(items = teams, key = { it.id }) { team ->
            TeamListItem(team = team, modifier = Modifier.testTag("home_team_item_${team.id}"))
        }
    }
}

@Composable
private fun HomeEmptyContent() {
    EmptyState(
        message = stringResource(R.string.home_empty_message),
        modifier = Modifier.testTag("home_empty_state"),
    )
}

@Composable
private fun HomeErrorContent(
    reason: HomeErrorReason,
    onIntent: (HomeUiIntent) -> Unit,
) {
    val title =
        when (reason) {
            HomeErrorReason.NETWORK_UNAVAILABLE -> stringResource(R.string.home_error_offline_title)
            HomeErrorReason.SERVER, HomeErrorReason.UNKNOWN -> stringResource(R.string.home_error_generic_title)
        }
    val message =
        when (reason) {
            HomeErrorReason.NETWORK_UNAVAILABLE -> ""
            HomeErrorReason.SERVER, HomeErrorReason.UNKNOWN -> stringResource(R.string.home_error_generic_message)
        }

    ErrorState(
        title = title,
        message = message,
        onRetry = { onIntent(HomeUiIntent.LoadTeams) },
        modifier = Modifier.testTag("home_error_state"),
        retryButtonTestTag = HOME_ERROR_RETRY_BUTTON_TEST_TAG,
    )
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    TgLabTheme {
        HomeScreen(
            state =
                HomeUiState.Content(
                    teams =
                        listOf(
                            TeamUiModel(id = 1, fullName = "Atlanta Hawks", city = "Atlanta", conference = "East"),
                            TeamUiModel(id = 2, fullName = "Boston Celtics", city = "Boston", conference = "East"),
                        ),
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    TgLabTheme {
        HomeScreen(state = HomeUiState.Loading, onIntent = {})
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    TgLabTheme {
        HomeScreen(state = HomeUiState.Empty, onIntent = {})
    }
}

@Preview
@Composable
private fun HomeScreenErrorPreview() {
    TgLabTheme {
        HomeScreen(state = HomeUiState.Error(reason = HomeErrorReason.SERVER), onIntent = {})
    }
}
