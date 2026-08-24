package band.effective.education.crossplatform.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.ui.components.FullScreenError
import band.effective.education.crossplatform.ui.components.FullScreenLoading
import band.effective.education.crossplatform.ui.screens.detail.model.PokemonDetailIntent
import band.effective.education.crossplatform.ui.screens.detail.model.PokemonDetailState

/**
 * Экран детали, как и список, ничего не решает: получает состояние, рисует его
 * и сообщает о намерениях. Каждое состояние — своя функция в своём файле.
 */
@Composable
fun PokemonDetailScreen(
    state: PokemonDetailState,
    onIntent: (PokemonDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is PokemonDetailState.Loading -> {
            FullScreenLoading(modifier)
        }

        is PokemonDetailState.Error -> {
            FullScreenError(
                message = state.message,
                onRetry = { onIntent(PokemonDetailIntent.RetryRequested) },
                modifier = modifier,
            )
        }

        is PokemonDetailState.Success -> {
            PokemonDetailSuccess(
                modifier = modifier,
                pokemon = state.pokemon,
                onIntent = onIntent,
            )
        }
    }
}
