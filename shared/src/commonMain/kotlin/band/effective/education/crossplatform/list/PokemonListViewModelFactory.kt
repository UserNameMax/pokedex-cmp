package band.effective.education.crossplatform.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.ui.navigation.Navigator
import kotlin.reflect.KClass

/**
 * Собирает [PokemonListViewModel] с её зависимостями.
 *
 * Фабрика нужна и сама по себе: в общем коде нет рефлексии, которой Android
 * создаёт ViewModel без аргументов, — на wasm и iOS её просто не существует.
 * А раз фабрику всё равно передавать явно, зависимости уровня приложения
 * живут в ней, а не протаскиваются через каждый экран.
 */
class PokemonListViewModelFactory(
    private val navigator: Navigator,
    private val repository: PokemonRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return PokemonListViewModel(navigator = navigator, repository = repository) as T
    }
}
