package band.effective.education.crossplatform.domain

import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Страницы каталога. Пока умеет только первую: какую страницу просить дальше
 * и когда записи кончились — это пагинация, тема чекпойнта 2.
 */
class LoadPokemonUseCase(
    private val pokemonRepository: PokemonRepository,
    private val pageSize: Int,
) {
    fun loadNext(): Flow<Result<Pokemon>> =
        pokemonRepository.getPokemons(offset = 0, limit = pageSize)
}
