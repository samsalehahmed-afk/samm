package com.salati.alsaghira.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.LayoutDirection

private val LightColors = lightColorScheme(
    primary = IslamicGreen,
    onPrimary = SoftWhite,
    primaryContainer = IslamicGreenLight,
    secondary = GoldAccent,
    onSecondary = TextDark,
    secondaryContainer = GoldAccentLight,
    background = CreamBackground,
    onBackground = TextDark,
    surface = SoftWhite,
    onSurface = TextDark,
    error = ErrorSoft
)

private val DarkColors = darkColorScheme(
    primary = IslamicGreenLight,
    onPrimary = TextDark,
    secondary = GoldAccent,
    background = IslamicGreenDark,
    onBackground = SoftWhite,
    surface = androidx.compose.ui.graphics.Color(0xFF1B1B1B),
    onSurface = SoftWhite
)

/**
 * App-wide theme. The whole app is Arabic-first, so layout direction
 * is forced to RTL regardless of system locale settings.
 */
@Composable
fun SalatiAlSaghiraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            content = content
        )
    }
}
