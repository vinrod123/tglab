package com.app.max.tglab.feature.home.presentation

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TeamListItem(
    team: TeamUiModel,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = { Text(team.fullName) },
        supportingContent = { Text(team.city) },
        trailingContent = { Text(team.conference) },
        modifier = modifier,
    )
}
