package band.effective.education.crossplatform

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import band.effective.education.crossplatform.data.PokemonRepositoryImpl
import band.effective.education.crossplatform.detail.PokemonDetailViewModelFactory
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.list.PokemonListViewModelFactory
import band.effective.education.crossplatform.ui.components.AppScaffold
import band.effective.education.crossplatform.ui.navigation.AppNavDisplay
import band.effective.education.crossplatform.ui.navigation.Navigator

@Composable
fun App() {
    MaterialTheme {
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
