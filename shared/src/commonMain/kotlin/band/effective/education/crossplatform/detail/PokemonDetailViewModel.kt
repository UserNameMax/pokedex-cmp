package band.effective.education.crossplatform.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.data.PokemonRepositoryImpl
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.ui.model.PokemonDetailUi
import band.effective.education.crossplatform.ui.model.toDetailUi
import band.effective.education.crossplatform.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Состояние детали меняется здесь, а не на экране — ровно как у списка.
 *
 * Своя ViewModel на каждую запись бэкстека: у bulbasaur и у venusaur, открытого
 * из его эволюции, разные экземпляры, и каждый знает только свой [pokemonId].
 *
 * Состояние — `null`, пока запись не прочитана. На моках ответ мгновенный, и
 * пустого кадра не видно; в В2 на его место встанет полноценное «загружается».
 */
class PokemonDetailViewModel(
    private val pokemonId: Int,
    private val navigator: Navigator,
    private val repository: PokemonRepository = PokemonRepositoryImpl(),
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailUi?>(null)
    val state: StateFlow<PokemonDetailUi?> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val pokemon = repository.getPokemon(pokemonId)
            val evolution = pokemon.evolutionChain.map { id -> repository.getPokemon(id) }
            _state.value = pokemon.toDetailUi(evolution)
        }
    }

    fun onIntent(intent: PokemonDetailIntent) {
        when (intent) {
            is PokemonDetailIntent.EvolutionStageClicked -> navigator.addToBackStack(Screen.Detail(intent.id))
        }
    }
}
