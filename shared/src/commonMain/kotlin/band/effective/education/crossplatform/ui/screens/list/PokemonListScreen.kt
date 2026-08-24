package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.ui.components.FullScreenError
import band.effective.education.crossplatform.ui.components.FullScreenLoading
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListIntent
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListState

/**
 * Экран ничего не решает: получает состояние, рисует его и сообщает о намерениях.
 * Ни моков, ни ViewModel он не видит — поэтому его можно нарисовать с любым состоянием.
 */
@Composable
fun PokemonListScreen(
    state: PokemonListState,
    onIntent: (PokemonListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is PokemonListState.Loading -> {
            FullScreenLoading(modifier)
        }

        is PokemonListState.Error -> {
            FullScreenError(
                message = state.message,
                onRetry = { onIntent(PokemonListIntent.RetryRequested) },
                modifier = modifier,
            )
        }

        is PokemonListState.Success -> {
            PokemonListSuccess(
                modifier = modifier,
                state = state,
                onIntent = onIntent,
            )
        }
    }
}
