package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonNotFound
import band.effective.education.crossplatform.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

/**
 * Реализация репозитория на моках из ПЗ1: те же двадцать записей, но уже в
 * форме, которую понимает остальной код, — страницами и по одной записи.
 *
 * На чекпойнте 1 рядом встаёт сетевая реализация, и в App меняется одна
 * строка. Всё, что выше data-слоя, при этом не меняется.
 */
class MockPokemonRepository : PokemonRepository {

    override fun getPokemons(offset: Int, limit: Int): Flow<Result<Pokemon>> =
        mockPokemons.drop(offset).take(limit).map { Result.success(it) }.asFlow()

    override suspend fun getPokemon(id: Int): Result<Pokemon> =
        mockPokemons.firstOrNull { it.id == id }?.let { Result.success(it) }
            ?: Result.failure(PokemonNotFound(id))
}
