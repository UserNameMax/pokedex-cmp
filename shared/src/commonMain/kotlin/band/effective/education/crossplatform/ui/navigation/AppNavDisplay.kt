package band.effective.education.crossplatform.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.detail.PokemonDetailViewModel
import band.effective.education.crossplatform.detail.PokemonDetailViewModelFactory
import band.effective.education.crossplatform.list.PokemonListViewModel
import band.effective.education.crossplatform.list.PokemonListViewModelFactory
import band.effective.education.crossplatform.ui.screens.detail.PokemonDetailScreen
import band.effective.education.crossplatform.ui.screens.list.PokemonListScreen

/** Длительность перехода между экранами. Одна на оба направления. */
private const val TRANSITION_MS = 300

@Composable
fun AppNavDisplay(
    modifier: Modifier,
    navigator: Navigator,
    listViewModelFactory: PokemonListViewModelFactory,
    detailViewModelFactory: PokemonDetailViewModelFactory,
) {
    val backStack by navigator.navStack.collectAsStateWithLifecycle()
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = navigator::back,
        // Все три спецификации задаются явно. Их значения по умолчанию
        // в артефакте Google на не-Android таргетах — заглушки, которые
        // бросают NotImplementedError.
        transitionSpec = { slide(SlideDirection.Start) },
        popTransitionSpec = { slide(SlideDirection.End) },
        predictivePopTransitionSpec = { slide(SlideDirection.End) },
        entryProvider = entryProvider {
            entry<Screen.List> {
                val viewModel: PokemonListViewModel = viewModel(factory = listViewModelFactory)
                val state by viewModel.state.collectAsStateWithLifecycle()
                PokemonListScreen(state = state, onIntent = viewModel::onIntent)
            }
            entry<Screen.Detail> { key ->
                // Ключ обязателен: без него viewModel() отдаёт один экземпляр на
                // весь хост, и деталь соседа по эволюции показала бы прошлую запись.
                val viewModel: PokemonDetailViewModel = viewModel(
                    key = "detail-${key.id}",
                    factory = detailViewModelFactory,
                    extras = PokemonDetailViewModelFactory.extrasFor(key.id),
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                state?.let { PokemonDetailScreen(pokemon = it, onIntent = viewModel::onIntent) }
            }
        },
    )
}

private fun AnimatedContentTransitionScope<*>.slide(
    direction: SlideDirection,
): ContentTransform =
    (slideIntoContainer(direction, tween(TRANSITION_MS)) + fadeIn(tween(TRANSITION_MS)))
        .togetherWith(
            slideOutOfContainer(direction, tween(TRANSITION_MS)) + fadeOut(tween(TRANSITION_MS)),
        )
