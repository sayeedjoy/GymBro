package com.sayeedjoy.gymbro.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00897B),       // Teal
    secondary = Color(0xFF80CBC4),
    background = Color(0xFFF7F9FA),
    surface = Color.White,
    onPrimary = Color.White,
    onSurface = Color.Black
)



@Composable
fun GymBroTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
