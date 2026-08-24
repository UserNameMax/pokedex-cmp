package band.effective.education.crossplatform.domain

import band.effective.education.crossplatform.domain.model.LoadStatus
import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonLocalRepository
import band.effective.education.crossplatform.domain.repository.PokemonNotFound
import band.effective.education.crossplatform.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex

/**
 * Дирижёр каталога: сам ничего не хранит и не грузит, а решает, кого и в каком
 * порядке позвать. Страницы — [LoadPokemonUseCase], записи — [band.effective.education.crossplatform.domain.repository.PokemonLocalRepository],
 * недостающее — [remote]. Экрану он отдаёт только чтение: список и статус.
 */
class GetPokemonInteractor(
    private val loadPokemonUseCase: LoadPokemonUseCase,
    private val remote: PokemonRepository,
    private val local: PokemonLocalRepository,
) {
    val pokemonList: Flow<List<Pokemon>> = local.observeAll()

    private val _status = MutableStateFlow<LoadStatus>(LoadStatus.Loading)

    /** Меняет его только дирижёр — снаружи статус не выставить. */
    val status: StateFlow<LoadStatus> = _status.asStateFlow()

    /** Одна загрузка за раз: координация параллельных команд — дело дирижёра. */
    private val mutex = Mutex()

    /**
     * Следующая страница. Вызов, пока идёт загрузка, ничего не делает: прокрутка
     * просит «ещё» много раз подряд, а запрос на каждый кадр нам не нужен.
     */
    suspend fun loadMore() {
        // tryLock, а не isLocked + withLock: между проверкой и захватом второй
        // вызов успел бы проскочить.
        if (!mutex.tryLock()) return
        try {
            loadNextPage()
        } finally {
            mutex.unlock()
        }
    }

    /** Сначала то, что уже лежит локально; не нашлось — из [remote]. Сбой хранилища наружу как есть. */
    suspend fun getById(id: Int): Result<Pokemon> {
        val cached = local.getPokemon(id)
        if (cached.exceptionOrNull() !is PokemonNotFound) return cached
        return remote.getPokemon(id)
    }

    private suspend fun loadNextPage() {
        _status.value = LoadStatus.Loading
        var failure: Throwable? = null
        loadPokemonUseCase.loadNext().collect { result ->
            result.fold(
                onSuccess = { local.upsert(it) },
                onFailure = { failure = it },
            )
        }
        // Статус — когда страница кончилась, а не на каждой записи: пустая
        // страница не пришлёт ни одной, и экран остался бы в Loading навсегда.
        _status.value = failure?.let { LoadStatus.Failed(it) } ?: LoadStatus.Loaded
    }
}
