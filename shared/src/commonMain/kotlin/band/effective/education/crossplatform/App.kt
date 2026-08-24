package band.effective.education.crossplatform

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import band.effective.education.crossplatform.data.InMemoryPokemonRepository
import band.effective.education.crossplatform.data.MockPokemonRepository
import band.effective.education.crossplatform.data.network.PokeApi
import band.effective.education.crossplatform.data.network.pokeApiHttpClient
import band.effective.education.crossplatform.domain.GetPokemonInteractor
import band.effective.education.crossplatform.domain.LoadPokemonUseCase
import band.effective.education.crossplatform.ui.screens.detail.view_model.PokemonDetailViewModelFactory
import band.effective.education.crossplatform.ui.screens.list.view_model.PokemonListViewModelFactory
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.action_toggle_theme
import band.effective.education.crossplatform.resources.ic_theme
import band.effective.education.crossplatform.ui.AppTheme
import band.effective.education.crossplatform.ui.components.AppScaffold
import band.effective.education.crossplatform.ui.navigation.AppNavDisplay
import band.effective.education.crossplatform.ui.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    var darkTheme by remember { mutableStateOf(false) }

    AppTheme(darkTheme) {
        // Один интерактор на всё приложение: оба экрана читают одни и те же данные.
        val getPokemonInteractor = remember {
            // Сервис собран и ждёт: на чекпойнте 1 моки меняются на сетевую
            // реализацию поверх него — одной строкой, остальная сборка та же.
            val pokeApi = PokeApi(pokeApiHttpClient("https://pokeapi.co/api/v2/"))
            val remote = MockPokemonRepository()
            val local = InMemoryPokemonRepository()
            val loadPokemonUseCase = LoadPokemonUseCase(remote, 20)
            GetPokemonInteractor(loadPokemonUseCase, remote, local)
        }
        val navigator = remember { Navigator() }
        // Здесь собираются зависимости всего приложения; экраны получают
        // готовые фабрики и не знают, из чего сделаны их ViewModel.
        val listViewModelFactory = remember { PokemonListViewModelFactory(
            navigator = navigator,
            getPokemonInteractor = getPokemonInteractor,
        ) }
        val detailViewModelFactory = remember { PokemonDetailViewModelFactory(navigator, getPokemonInteractor) }
        val navStack = navigator.navStack.collectAsStateWithLifecycle()
        val backNavigationIsAvailable by remember { derivedStateOf { navStack.value.size > 1 } }

        AppScaffold(
            onBack = navigator::back.takeIf { backNavigationIsAvailable },
            actions = {
                IconButton(onClick = { darkTheme = !darkTheme }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_theme),
                        contentDescription = stringResource(Res.string.action_toggle_theme),
                    )
                }
            },
        ) { modifier ->
            AppNavDisplay(
                modifier = modifier,
                navigator = navigator,
                listViewModelFactory = listViewModelFactory,
                detailViewModelFactory = detailViewModelFactory,
            )
        }
    }
}
