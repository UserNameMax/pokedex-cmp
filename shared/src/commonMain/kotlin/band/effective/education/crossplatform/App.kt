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
import band.effective.education.crossplatform.data.PokemonRepositoryImpl
import band.effective.education.crossplatform.detail.PokemonDetailViewModelFactory
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.list.PokemonListViewModelFactory
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
        // Один репозиторий на всё приложение: оба экрана читают одни и те же данные.
        val repository: PokemonRepository = remember { PokemonRepositoryImpl() }
        val navigator = remember { Navigator() }
        // Здесь собираются зависимости всего приложения; экраны получают
        // готовые фабрики и не знают, из чего сделаны их ViewModel.
        val listViewModelFactory = remember { PokemonListViewModelFactory(navigator, repository) }
        val detailViewModelFactory = remember { PokemonDetailViewModelFactory(navigator, repository) }
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
