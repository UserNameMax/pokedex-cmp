package band.effective.education.crossplatform.ui.screens.detail.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.domain.GetPokemonInteractor
import band.effective.education.crossplatform.ui.model.PokemonDetailUi
import band.effective.education.crossplatform.ui.model.toDetailUi
import band.effective.education.crossplatform.ui.navigation.Navigator
import band.effective.education.crossplatform.ui.screens.detail.model.PokemonDetailIntent
import band.effective.education.crossplatform.ui.screens.detail.model.PokemonDetailState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Деталь — свой запрос, свой экран, своя корутина. Ровно то, о чём был гвоздь
 * лекции 27.10: `viewModelScope` — родитель. Ушли с экрана — по идее Navigation 3
 * должен очистить ViewModel записи бэкстека через [rememberViewModelStoreNavEntryDecorator],
 * и viewModelScope отменится сам, без единой ручной отмены.
 *
 * **Не проверено на паре до конца.** `onCleared()` ни разу не сработал при живом
 * прогоне на wasmJs (см. `WORKSHOP-SPEC-pz4.md`, открытые вопросы) — проверить на
 * Android перед 31.10.
 *
 * @param navigator переход на соседа по эволюции — та же навигация наружу, что и у списка.
 */
class PokemonDetailViewModel(
    private val pokemonId: Int,
    private val navigator: Navigator,
    private val getPokemonInteractor: GetPokemonInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    init {
        load()
    }

    private fun load() {
        _state.value = PokemonDetailState.Loading
        viewModelScope.launch {
            // Отмена сюда не доходит как ошибка: обёртка в PokeApi её
            // пробрасывает, и корутина просто останавливается вместе с viewModelScope.
            _state.value = loadDetail().fold(
                onSuccess = { PokemonDetailState.Success(it) },
                onFailure = { e -> PokemonDetailState.Error(e.message ?: "network error") },
            )
        }
    }

    private suspend fun loadDetail(): Result<PokemonDetailUi> {
        // Сама запись — из уже загруженного списка, если она там есть, иначе три
        // запроса, два из них параллельно (см. GetPokemonInteractor.getById).
        val pokemon = getPokemonInteractor.getById(pokemonId).getOrElse { return Result.failure(it) }

        // Соседи по эволюции: N независимых запросов, запущенных одновременно,
        // а не по одному. coroutineScope, а не голый async снаружи: дети
        // привязаны к этой корутине и отменяются вместе с ней.
        val stages = coroutineScope {
            pokemon.evolutionChain
                .map { id -> async { getPokemonInteractor.getById(id) } }
                .awaitAll()
        }.map { it.getOrElse { e -> return Result.failure(e) } }

        return Result.success(pokemon.toDetailUi(evolution = stages))
    }

    fun onIntent(intent: PokemonDetailIntent) {
        when (intent) {
            is PokemonDetailIntent.EvolutionStageClicked -> navigator.addToBackStack(Screen.Detail(intent.id))
            is PokemonDetailIntent.RetryRequested -> load()
        }
    }
}
