package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.list_error
import band.effective.education.crossplatform.resources.list_retry
import org.jetbrains.compose.resources.stringResource

/**
 * Ошибка на весь экран с «Повторить» — общая для списка и детали. Что именно
 * повторять, компонент не знает: это решает экран через [onRetry].
 */
@Composable
fun FullScreenError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.list_error, message),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp),
            )
            Button(onClick = onRetry) {
                Text(stringResource(Res.string.list_retry))
            }
        }
    }
}
