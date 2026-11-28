package com.app.max.tglab.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role

@Composable
fun TeamListItem(
    team: TeamUiModel,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val clickableModifier =
        if (onClick != null) {
            modifier.clickable(
                onClickLabel = stringResource(R.string.home_team_item_click_label),
                role = Role.Button,
                onClick = onClick,
            )
        } else {
            modifier
        }
    ListItem(
        headlineContent = { Text(team.fullName) },
        supportingContent = { Text(team.city) },
        trailingContent = { Text(team.conference) },
        modifier = clickableModifier,
    )
}
