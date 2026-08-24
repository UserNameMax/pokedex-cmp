package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonLocalRepository
import band.effective.education.crossplatform.domain.repository.PokemonNotFound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.update

/**
 * Локальное хранилище в памяти — мостик к настоящему: живёт, пока живёт
 * приложение. Когда понадобится переживать перезапуск, на его место встанет
 * реализация поверх диска, а [PokemonLocalRepository] останется тем же.
 */
class InMemoryPokemonRepository : PokemonLocalRepository {

    private val pokemons = MutableStateFlow(emptyList<Pokemon>())

    override fun observeAll(): Flow<List<Pokemon>> = pokemons.asStateFlow()

    override fun getPokemons(offset: Int, limit: Int): Flow<Result<Pokemon>> =
        pokemons.value.drop(offset).take(limit).map { Result.success(it) }.asFlow()

    override suspend fun getPokemon(id: Int): Result<Pokemon> =
        pokemons.value.firstOrNull { it.id == id }?.let { Result.success(it) }
            ?: Result.failure(PokemonNotFound(id))

    override suspend fun upsert(pokemon: Pokemon) {
        pokemons.update { list ->
            val index = list.indexOfFirst { it.id == pokemon.id }
            if (index >= 0) list.toMutableList().also { it[index] = pokemon } else list + pokemon
        }
    }

    override suspend fun clear() {
        pokemons.value = emptyList()
    }
}
