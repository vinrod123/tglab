package com.app.max.tglab.feature.teamdetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun GameListItem(
    game: GameUiModel,
    modifier: Modifier = Modifier,
) {
    val combinedDescription =
        stringResource(
            R.string.team_detail_game_item_description,
            game.homeTeamName,
            game.homeTeamScore,
            game.visitorTeamName,
            game.visitorTeamScore,
        )
    ListItem(
        modifier = modifier.semantics { contentDescription = combinedDescription },
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = game.homeTeamName,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = game.homeTeamScore.toString())
                    Text(text = stringResource(R.string.team_detail_game_score_separator))
                    Text(text = game.visitorTeamScore.toString())
                }
                Text(
                    text = game.visitorTeamName,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                )
            }
        },
    )
}
