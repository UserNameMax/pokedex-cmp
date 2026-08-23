package band.effective.education.crossplatform.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFB3261E),
    surface = Color(0xFFFFFBFE),
    background = Color(0xFFFFFBFE),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFF2B8B5),
    surface = Color(0xFF1C1B1F),
    background = Color(0xFF141218),
)

/**
 * Тема приложения. Цвета объявлены здесь и только здесь: всё остальное берёт их
 * через MaterialTheme.colorScheme, иначе переключение темы обойдёт элемент стороной.
 */
@Composable
fun AppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
