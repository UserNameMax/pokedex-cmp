package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Готовая подложка под карточку списка: скруглённые углы, тень, отступ содержимого.
 *
 * Цвет берётся из токенов темы, а не задаётся числом. Числовой цвет в переключении
 * темы не участвует — и заодно уносит с собой контрастный цвет текста, потому что
 * Surface выводит его из своего цвета.
 */
@Composable
fun CardSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
    ) {
        Box(Modifier.padding(12.dp)) { content() }
    }
}
