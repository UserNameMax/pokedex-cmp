package band.effective.education.crossplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import band.effective.education.crossplatform.list.PokemonListViewModel
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.start_hint
import band.effective.education.crossplatform.ui.AppScaffold
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    MaterialTheme {
        // ViewModel живёт дольше композиции: пересоздание экрана её не сбросит.
        val viewModel: PokemonListViewModel = viewModel { PokemonListViewModel(onOpenCard = {}) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppScaffold { modifier ->
            Box(modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(Res.string.start_hint, state.items.size.toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
