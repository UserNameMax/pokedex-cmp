package band.effective.education.crossplatform.ui.screens.list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.domain.GetPokemonInteractor
import band.effective.education.crossplatform.domain.model.LoadStatus
import band.effective.education.crossplatform.ui.model.toCardsUi
import band.effective.education.crossplatform.ui.navigation.Navigator
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListIntent
import band.effective.education.crossplatform.ui.screens.list.model.PokemonListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Единственное место, где состояние экрана меняется.
 *
 * Данные ViewModel берёт у [GetPokemonInteractor], а откуда тот берёт записи —
 * моки или сеть — его дело. Когда на чекпойнте 1 моки сменятся сетью, здесь
 * не поменяется ни строки.
 *
 * @param navigator навигация наружу. ViewModel не знает про NavDisplay и его
 * бэкстек — только про [Navigator]: иначе экран нельзя было бы ни
 * переиспользовать, ни протестировать.
 */
class PokemonListViewModel(
    private val navigator: Navigator,
    private val getPokemonInteractor: GetPokemonInteractor,
) : ViewModel() {

    /**
     * Текст поиска — состояние этого экрана, а не данные: он не нужен никому,
     * кроме списка, и живёт столько же, сколько ViewModel.
     */
    private val query = MutableStateFlow("")

    /** Состояние экрана — чистая функция от входов: списка, запроса и хода загрузки. */
    val state: StateFlow<PokemonListState> = combine(
        getPokemonInteractor.pokemonList,
        query,
        getPokemonInteractor.status,
    ) { pokemon, query, status ->
        PokemonListState(
            query = query,
            items = pokemon.toCardsUi(query),
            // Догрузка — это загрузка при уже показанном списке.
            isLoadingMore = status is LoadStatus.Loading && pokemon.isNotEmpty(),
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, PokemonListState())

    init {
        loadNextPage()
    }

    private fun loadNextPage() {
        viewModelScope.launch { getPokemonInteractor.loadMore() }
    }

    fun onIntent(intent: PokemonListIntent) {
        when (intent) {
            is PokemonListIntent.CardClicked -> navigator.addToBackStack(Screen.Detail(intent.id))

            is PokemonListIntent.QueryChanged -> query.value = intent.value

            is PokemonListIntent.LoadMoreRequested -> loadNextPage()
        }
    }
}
