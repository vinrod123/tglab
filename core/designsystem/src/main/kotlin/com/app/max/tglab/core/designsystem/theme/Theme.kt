package com.app.max.tglab.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TgLabLightColorScheme =
    lightColorScheme(
        primary = TgLabPrimary,
        onPrimary = TgLabOnPrimary,
        secondary = TgLabSecondary,
        onSecondary = TgLabOnSecondary,
        background = TgLabBackground,
        onBackground = TgLabOnBackground,
        surface = TgLabSurface,
        onSurface = TgLabOnSurface,
        error = TgLabError,
        onError = TgLabOnError,
    )

private val TgLabDarkColorScheme =
    darkColorScheme(
        primary = TgLabPrimaryDark,
        onPrimary = TgLabOnPrimaryDark,
        secondary = TgLabSecondaryDark,
        onSecondary = TgLabOnSecondaryDark,
        background = TgLabBackgroundDark,
        onBackground = TgLabOnBackgroundDark,
        surface = TgLabSurfaceDark,
        onSurface = TgLabOnSurfaceDark,
        error = TgLabErrorDark,
        onError = TgLabOnErrorDark,
    )

@Composable
fun TgLabTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (useDarkTheme) TgLabDarkColorScheme else TgLabLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = TgLabTypography,
        shapes = TgLabShapes,
        content = content,
    )
}
