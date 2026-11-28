package com.app.max.tglab.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource

private const val DEFAULT_RETRY_BUTTON_TEST_TAG = "error_state_retry_button"

@Composable
fun ErrorState(
    title: String,
    message: String,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier,
    retryButtonTestTag: String = DEFAULT_RETRY_BUTTON_TEST_TAG,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null)
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        if (message.isNotBlank()) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        if (onRetry != null) {
            Button(
                onClick = onRetry,
                modifier = Modifier.testTag(retryButtonTestTag),
            ) {
                Text(text = stringResource(R.string.core_ui_retry_button_label))
            }
        }
    }
}
