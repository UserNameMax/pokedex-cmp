package band.effective.education.crossplatform.list

import androidx.lifecycle.ViewModel
import band.effective.education.crossplatform.data.mockPokemons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Единственное место, где состояние экрана меняется.
 *
 * Наружу отдаётся [StateFlow] только на чтение: изменить состояние снаружи нельзя,
 * можно лишь прислать намерение в [onIntent]. `ViewModel` здесь — гугловская,
 * из `androidx.lifecycle`, и она мультиплатформенная: тот же класс работает
 * на Android, web и desktop.
 *
 * Данные пока берутся из моков. В В2 на их место встанет сеть, и поменяется
 * только этот файл — если поменяется что-то ещё, слои разложены неверно.
 *
 * @param onOpenCard навигация наружу. ViewModel не знает про NavController:
 * иначе экран нельзя было бы ни переиспользовать, ни протестировать.
 */
class PokemonListViewModel(
    private val onOpenCard: (Int) -> Unit,
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState(items = mockPokemons))
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    fun onIntent(intent: PokemonListIntent) {
        when (intent) {
            is PokemonListIntent.CardClicked -> onOpenCard(intent.id)
        }
    }
}
