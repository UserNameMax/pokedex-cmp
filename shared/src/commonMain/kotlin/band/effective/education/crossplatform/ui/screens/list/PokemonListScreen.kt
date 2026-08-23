package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.list.PokemonListIntent
import band.effective.education.crossplatform.list.PokemonListState
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.search_hint
import org.jetbrains.compose.resources.stringResource

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
    Column(modifier) {
        OutlinedTextField(
            value = state.query,
            // Экран не хранит запрос у себя: он сообщает о намерении, а новое
            // состояние приезжает сверху. Двух источников правды нет.
            onValueChange = { onIntent(PokemonListIntent.QueryChanged(it)) },
            label = { Text(stringResource(Res.string.search_hint)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        )
        LazyColumn(
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
}
