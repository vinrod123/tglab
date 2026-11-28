package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.max.tglab.core.designsystem.theme.TgLabTheme
import com.app.max.tglab.core.ui.EmptyState
import com.app.max.tglab.core.ui.ErrorState
import com.app.max.tglab.core.ui.LoadingState

private const val TEAM_DETAIL_ERROR_RETRY_BUTTON_TEST_TAG = "team_detail_error_retry_button"
private val TEAM_DETAIL_TOP_CORNER_RADIUS = 16.dp

@Composable
fun TeamDetailScreen(
    teamName: String,
    state: TeamDetailUiState,
    onIntent: (TeamDetailUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val topCornersShape =
        RoundedCornerShape(
            topStart = TEAM_DETAIL_TOP_CORNER_RADIUS,
            topEnd = TEAM_DETAIL_TOP_CORNER_RADIUS,
        )
    Scaffold(
        modifier =
            modifier
                .testTag("team_detail_screen")
                .clip(topCornersShape),
        topBar = {
            TeamDetailTopBar(
                teamName = teamName,
                onBackClick = { onIntent(TeamDetailUiIntent.NavigateBack) },
            )
        },
    ) { contentPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
            when (state) {
                is TeamDetailUiState.Loading -> TeamDetailLoadingContent()
                is TeamDetailUiState.Content -> TeamDetailGamesList(games = state.games)
                is TeamDetailUiState.Empty -> TeamDetailEmptyContent()
                is TeamDetailUiState.Error -> TeamDetailErrorContent(reason = state.reason, onIntent = onIntent)
            }
        }
    }
}

@Composable
private fun TeamDetailTopBar(
    teamName: String,
    onBackClick: () -> Unit,
) {
    val backButtonDescription = stringResource(R.string.team_detail_back_button_label)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            onClick = onBackClick,
            modifier =
                Modifier
                    .testTag("team_detail_back_button")
                    .clearAndSetSemantics { contentDescription = backButtonDescription },
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Text(text = stringResource(R.string.team_detail_back_button_text))
        }
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f).testTag("team_detail_team_name"),
        )
    }
}

@Composable
private fun TeamDetailLoadingContent() {
    LoadingState(
        message = stringResource(R.string.team_detail_loading_message),
        modifier = Modifier.testTag("team_detail_loading_state"),
    )
}

@Composable
private fun TeamDetailGamesList(games: List<GameUiModel>) {
    LazyColumn(modifier = Modifier.testTag("team_detail_games_list")) {
        items(items = games, key = { it.id }) { game ->
            GameListItem(game = game, modifier = Modifier.testTag("team_detail_game_item_${game.id}"))
        }
    }
}

@Composable
private fun TeamDetailEmptyContent() {
    EmptyState(
        message = stringResource(R.string.team_detail_empty_message),
        modifier = Modifier.testTag("team_detail_empty_state"),
    )
}

@Composable
private fun TeamDetailErrorContent(
    reason: TeamDetailErrorReason,
    onIntent: (TeamDetailUiIntent) -> Unit,
) {
    val title =
        when (reason) {
            TeamDetailErrorReason.NETWORK_UNAVAILABLE -> stringResource(R.string.team_detail_error_offline_title)
            TeamDetailErrorReason.SERVER, TeamDetailErrorReason.UNKNOWN ->
                stringResource(R.string.team_detail_error_generic_title)
        }
    val message =
        when (reason) {
            TeamDetailErrorReason.NETWORK_UNAVAILABLE -> ""
            TeamDetailErrorReason.SERVER, TeamDetailErrorReason.UNKNOWN ->
                stringResource(R.string.team_detail_error_generic_message)
        }

    ErrorState(
        title = title,
        message = message,
        onRetry = { onIntent(TeamDetailUiIntent.LoadGames) },
        modifier = Modifier.testTag("team_detail_error_state"),
        retryButtonTestTag = TEAM_DETAIL_ERROR_RETRY_BUTTON_TEST_TAG,
    )
}

@Preview
@Composable
private fun TeamDetailScreenContentPreview() {
    TgLabTheme {
        TeamDetailScreen(
            teamName = "Boston Celtics",
            state =
                TeamDetailUiState.Content(
                    games =
                        listOf(
                            GameUiModel(
                                id = 1,
                                homeTeamName = "Atlanta Hawks",
                                homeTeamScore = 102,
                                visitorTeamName = "Boston Celtics",
                                visitorTeamScore = 98,
                            ),
                        ),
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun TeamDetailScreenLoadingPreview() {
    TgLabTheme {
        TeamDetailScreen(teamName = "Boston Celtics", state = TeamDetailUiState.Loading, onIntent = {})
    }
}

@Preview
@Composable
private fun TeamDetailScreenEmptyPreview() {
    TgLabTheme {
        TeamDetailScreen(teamName = "Boston Celtics", state = TeamDetailUiState.Empty, onIntent = {})
    }
}

@Preview
@Composable
private fun TeamDetailScreenErrorPreview() {
    TgLabTheme {
        TeamDetailScreen(
            teamName = "Boston Celtics",
            state = TeamDetailUiState.Error(reason = TeamDetailErrorReason.SERVER),
            onIntent = {},
        )
    }
}
