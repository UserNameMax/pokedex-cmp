package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.list.PokemonListIntent
import band.effective.education.crossplatform.list.PokemonListState

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
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(state.items, key = { it.id }) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onIntent(PokemonListIntent.CardClicked(pokemon.id)) },
            )
        }
    }
}
