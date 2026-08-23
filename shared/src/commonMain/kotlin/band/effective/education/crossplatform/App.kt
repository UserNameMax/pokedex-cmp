package band.effective.education.crossplatform

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import band.effective.education.crossplatform.list.PokemonListViewModel
import band.effective.education.crossplatform.ui.components.AppScaffold
import band.effective.education.crossplatform.ui.screens.list.PokemonListScreen

@Composable
fun App() {
    MaterialTheme {
        val viewModel: PokemonListViewModel = viewModel { PokemonListViewModel(onOpenCard = {}) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppScaffold { modifier ->
            PokemonListScreen(
                state = state,
                onIntent = viewModel::onIntent,
                modifier = modifier,
            )
        }
    }
}
