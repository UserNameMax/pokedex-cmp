package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.Pokemon
import band.effective.education.crossplatform.domain.PokemonRepository

/**
 * Реализация репозитория на моках: отдаёт доменные записи сразу, без сети.
 *
 * В В2 этот класс переписывается — ответ сети разбирается из DTO в те же
 * доменные модели. Всё, что выше data-слоя, при этом не меняется.
 */
class PokemonRepositoryImpl : PokemonRepository {
    override suspend fun getPokemons(): List<Pokemon> = mockPokemons

    override suspend fun getPokemon(id: Int): Pokemon = mockPokemons.first { it.id == id }
}
