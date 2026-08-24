package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.list_load_more_failed
import band.effective.education.crossplatform.resources.list_retry
import band.effective.education.crossplatform.resources.list_empty
import band.effective.education.crossplatform.resources.list_loading_next
import band.effective.education.crossplatform.resources.search_hint
import band.effective.education.crossplatform.ui.model.PokemonCardUi
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListIntent
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListState
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonListSuccess(
    modifier: Modifier,
    state: PokemonListState.Success,
    onIntent: (PokemonListIntent) -> Unit,
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
        if (state.items.isEmpty()) {
            PokemonEmptyList()
        } else {
            PokemonListBody(
                items = state.items,
                isLoadingMore = state.isLoadingMore,
                loadMoreFailed = state.loadMoreFailed,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun PokemonEmptyList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(Res.string.list_empty))
    }
}

@Composable
private fun PokemonListBody(
    items: List<PokemonCardUi>,
    isLoadingMore: Boolean,
    loadMoreFailed: Boolean,
    onIntent: (PokemonListIntent) -> Unit,
) {
    val listState = rememberLazyListState()
    // derivedStateOf смотрит только на listState — он remember-ится один раз
    // и не должен замыкать items.size: тот меняется на каждой странице,
    // а лямбда derivedStateOf от первой композиции не обновится следом за ним.
    val lastVisibleIndex by remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
    }
    // Список сам не грузит следующую страницу — он только замечает, что
    // подъехал к концу, и сообщает об этом намерением, как любое другое действие.
    LaunchedEffect(lastVisibleIndex, items.size) {
        if (lastVisibleIndex >= items.size - 3) onIntent(PokemonListIntent.LoadMoreRequested)
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items, key = { it.id }) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onIntent(PokemonListIntent.CardClicked(pokemon.id)) },
            )
        }
        if (isLoadingMore) {
            item {
                LoadingMoreIndicator(Modifier.fillMaxWidth().padding(16.dp))
            }
        }
        if (loadMoreFailed) {
            item {
                LoadingMoreError(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    onIntent(
                        PokemonListIntent.RetryRequested
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingMoreIndicator(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(stringResource(Res.string.list_loading_next))
    }
}

@Composable
private fun LoadingMoreError(
    modifier: Modifier,
    onRetry: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.list_load_more_failed),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f),
        )
        TextButton(onClick = onRetry) {
            Text(stringResource(Res.string.list_retry))
        }
    }
}