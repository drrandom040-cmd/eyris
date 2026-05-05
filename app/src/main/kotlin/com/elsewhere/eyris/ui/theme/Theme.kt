package com.elsewhere.eyris.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7C3AED),           // Electric Violet
    onPrimary = Color(0xFFF1F5F9),         // Off White
    primaryContainer = Color(0xFF1A1A2E),  // Deep Indigo
    onPrimaryContainer = Color(0xFFF1F5F9),
    
    secondary = Color(0xFF16213E),         // Dark Slate
    onSecondary = Color(0xFFF1F5F9),
    secondaryContainer = Color(0xFF0F3460), // Muted Navy
    onSecondaryContainer = Color(0xFFF1F5F9),
    
    tertiary = Color(0xFF94A3B8),          // Cool Grey
    onTertiary = Color(0xFF1A1A2E),
    tertiaryContainer = Color(0xFF0F3460),
    onTertiaryContainer = Color(0xFFF1F5F9),
    
    error = Color(0xFFEF4444),             // Red
    onError = Color(0xFF1A1A2E),
    errorContainer = Color(0xFFEF4444),
    onErrorContainer = Color(0xFFF1F5F9),
    
    background = Color(0xFF1A1A2E),        // Deep Indigo
    onBackground = Color(0xFFF1F5F9),
    
    surface = Color(0xFF16213E),           // Dark Slate
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF0F3460),
    onSurfaceVariant = Color(0xFF94A3B8),
    
    outline = Color(0xFF94A3B8),
    outlineVariant = Color(0xFF0F3460),
    scrim = Color(0xFF000000)
)

@Composable
fun EyrisTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = EyrisTypography,
        content = content
    )
}
