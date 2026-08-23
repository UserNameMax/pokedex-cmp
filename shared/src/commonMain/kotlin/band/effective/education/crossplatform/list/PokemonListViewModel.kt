package band.effective.education.crossplatform.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.Screen
import band.effective.education.crossplatform.data.PokemonRepositoryImpl
import band.effective.education.crossplatform.domain.PokemonRepository
import band.effective.education.crossplatform.ui.model.toCardsUi
import band.effective.education.crossplatform.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Единственное место, где состояние экрана меняется.
 *
 * Наружу отдаётся [StateFlow] только на чтение: изменить состояние снаружи нельзя,
 * можно лишь прислать намерение в [onIntent]. `ViewModel` здесь — гугловская,
 * из `androidx.lifecycle`, и она мультиплатформенная: тот же класс работает
 * на Android, web и desktop.
 *
 * Данные ViewModel берёт у [PokemonRepository] — доменного интерфейса, а не у
 * моков напрямую. В В2 за этим интерфейсом встанет сеть, и поменяется только
 * реализация в data-слое — если поменяется что-то ещё, слои разложены неверно.
 *
 * @param navigator навигация наружу. ViewModel не знает про NavDisplay и его
 * бэкстек — только про [Navigator]: иначе экран нельзя было бы ни
 * переиспользовать, ни протестировать.
 */
class PokemonListViewModel(
    private val navigator: Navigator,
    private val repository: PokemonRepository = PokemonRepositoryImpl(),
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val items = repository.getPokemons().toCardsUi()
            _state.update { it.copy(items = items) }
        }
    }

    fun onIntent(intent: PokemonListIntent) {
        when (intent) {
            is PokemonListIntent.CardClicked -> navigator.addToBackStack(Screen.Detail(intent.id))
        }
    }
}
