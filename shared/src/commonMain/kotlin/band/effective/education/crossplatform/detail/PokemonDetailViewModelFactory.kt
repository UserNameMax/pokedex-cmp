package band.effective.education.crossplatform.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.ui.navigation.Navigator
import kotlin.reflect.KClass

/**
 * Собирает [PokemonDetailViewModel].
 *
 * Зависимости уровня приложения фабрика получает один раз, а номер записи у
 * каждой детали свой — он приходит через [CreationExtras] при создании.
 */
class PokemonDetailViewModelFactory(
    private val navigator: Navigator,
    private val repository: PokemonRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val pokemonId = checkNotNull(extras[PokemonIdKey]) { "pokemonId не передан в CreationExtras" }
        @Suppress("UNCHECKED_CAST")
        return PokemonDetailViewModel(
            pokemonId = pokemonId,
            navigator = navigator,
            repository = repository,
        ) as T
    }

    companion object {
        val PokemonIdKey = CreationExtras.Key<Int>()

        /** Параметры создания для детали конкретной записи. */
        fun extrasFor(pokemonId: Int): CreationExtras =
            MutableCreationExtras().apply { set(PokemonIdKey, pokemonId) }
    }
}
