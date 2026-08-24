package band.effective.education.crossplatform.ui.screens.detail.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.domain.GetPokemonInteractor
import band.effective.education.crossplatform.ui.model.PokemonDetailUi
import band.effective.education.crossplatform.ui.model.toDetailUi
import band.effective.education.crossplatform.ui.navigation.Navigator
import band.effective.education.crossplatform.ui.screens.detail.model.PokemonDetailIntent
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
 * пустого кадра не видно; на чекпойнте 4 на его место встанет полноценное
 * «загружается» и «ошибка».
 */
class PokemonDetailViewModel(
    private val pokemonId: Int,
    private val navigator: Navigator,
    private val getPokemonInteractor: GetPokemonInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailUi?>(null)
    val state: StateFlow<PokemonDetailUi?> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPokemonInteractor.getById(pokemonId).fold(
                onSuccess = { pokemon ->
                    val evolution = pokemon.evolutionChain.mapNotNull { id -> getPokemonInteractor.getById(id).getOrNull() }
                    _state.value = pokemon.toDetailUi(evolution)
                },
                // На моках не падает. Состояние ошибки появится на чекпойнте 4.
                onFailure = { },
            )
        }
    }

    fun onIntent(intent: PokemonDetailIntent) {
        when (intent) {
            is PokemonDetailIntent.EvolutionStageClicked -> navigator.addToBackStack(Screen.Detail(intent.id))
        }
    }
}
