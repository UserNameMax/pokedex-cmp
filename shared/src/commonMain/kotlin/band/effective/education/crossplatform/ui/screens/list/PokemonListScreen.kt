package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.list_loading_next
import band.effective.education.crossplatform.resources.search_hint
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListIntent
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListState
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
        val listState = rememberLazyListState()
        // derivedStateOf смотрит только на listState — он remember-ится один раз
        // и не должен замыкать state.items.size: тот меняется на каждой странице,
        // а лямбда derivedStateOf от первой композиции не обновится следом за ним.
        val lastVisibleIndex by remember {
            derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
        }
        // Список сам не грузит следующую страницу — он только замечает, что
        // подъехал к концу, и сообщает об этом намерением, как любое другое действие.
        LaunchedEffect(lastVisibleIndex, state.items.size) {
            if (lastVisibleIndex >= state.items.size - 3) onIntent(PokemonListIntent.LoadMoreRequested)
        }

        LazyColumn(
            state = listState,
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
            if (state.isLoadingMore) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(Res.string.list_loading_next))
                    }
                }
            }
        }
    }
}
