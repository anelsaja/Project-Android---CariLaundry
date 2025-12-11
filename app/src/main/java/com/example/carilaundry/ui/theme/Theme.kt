package com.example.carilaundry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    background = Background,
    surface = Surface,
    onBackground = OnBackground,
    error = Error
)

private val DarkColors = darkColorScheme(
    primary = Blue100,
    onPrimary = OnPrimary,
    primaryContainer = Blue40,
    background = Color(0xFF0B1320),
    surface = Color(0xFF081026),
    onBackground = Color(0xFFE6F0FB),
    error = Error
)

@Composable
fun CariLaundryTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
