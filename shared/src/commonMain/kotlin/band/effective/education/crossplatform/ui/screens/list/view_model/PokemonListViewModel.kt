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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_MS = 400L

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
@OptIn(FlowPreview::class)
class PokemonListViewModel(
    private val navigator: Navigator,
    private val getPokemonInteractor: GetPokemonInteractor,
) : ViewModel() {

    /**
     * Текст поиска — состояние этого экрана, а не данные: он не нужен никому,
     * кроме списка, и живёт столько же, сколько ViewModel.
     */
    private val query = MutableStateFlow("")

    /**
     * Запрос, который реально применён к списку, — не то же самое, что текст
     * поля. Дебаунс — надстройка над готовым поиском: фильтрация не поменялась,
     * поменялось только КОГДА она получает новый запрос. Поле при этом
     * показывает живой [query] и от нажатий не отстаёт.
     */
    private val appliedQuery: StateFlow<String> = query
        .drop(1)
        .debounce(SEARCH_DEBOUNCE_MS)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    /**
     * Состояние экрана — чистая функция от входов: списка, запроса и хода
     * загрузки. Какой из вариантов суммы показать, решает пара «есть ли что
     * показать» и «как прошла последняя страница».
     */
    val state: StateFlow<PokemonListState> = combine(
        getPokemonInteractor.pokemonList,
        query,
        appliedQuery,
        getPokemonInteractor.status,
    ) { pokemon, query, appliedQuery, status ->
        if (pokemon.isEmpty()) {
            when (status) {
                LoadStatus.Loading -> PokemonListState.Loading
                is LoadStatus.Failed -> PokemonListState.Error(status.cause.message ?: "network error")
                // Страница пришла пустой — показывать нечего, но это не ошибка.
                LoadStatus.Loaded -> PokemonListState.Success(query = query, items = emptyList())
            }
        } else {
            // Есть что показать — показываем всегда. Пока страница грузится,
            // карточки появляются по одной, а внизу — «загружается».
            PokemonListState.Success(
                query = query,
                items = pokemon.toCardsUi(appliedQuery),
                isLoadingMore = status is LoadStatus.Loading,
                loadMoreFailed = status is LoadStatus.Failed,
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, PokemonListState.Loading)

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

            // Повтор — та же следующая страница: offset после провала не сдвинулся,
            // и запросится ровно то, что не загрузилось. И с экрана ошибки, и снизу списка.
            is PokemonListIntent.RetryRequested -> loadNextPage()
        }
    }
}
