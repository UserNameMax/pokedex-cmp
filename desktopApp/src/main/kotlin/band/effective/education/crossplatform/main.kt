package band.effective.education.crossplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.stringResource



fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        // заголовок окна — тоже подпись интерфейса, значит живёт в ресурсах
        title = "test",
    ) {
        App()
    }
}
