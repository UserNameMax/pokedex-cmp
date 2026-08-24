package band.effective.education.crossplatform.domain

import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Страницы каталога: какую просить дальше. */
class LoadPokemonUseCase(
    private val pokemonRepository: PokemonRepository,
    private val pageSize: Int,
) {
    /** С какого места каталога просить следующую страницу. */
    private var offset = 0

    /**
     * Следующая страница, записями по одной. Offset сдвигается только после
     * страницы без ошибок: упавшая страница при следующем вызове запросится
     * снова, а не пропустится навсегда.
     */
    fun loadNext(): Flow<Result<Pokemon>> = flow {
        var failed = false
        pokemonRepository.getPokemons(offset = offset, limit = pageSize).collect { result ->
            if (result.isFailure) failed = true
            emit(result)
        }
        if (!failed) {
            offset += pageSize
        }
    }
}
