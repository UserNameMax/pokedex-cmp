package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.data.network.PokeApi
import band.effective.education.crossplatform.data.network.createPokemon
import band.effective.education.crossplatform.data.network.dto.flattenIds
import band.effective.education.crossplatform.data.network.dto.idFromUrl
import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import band.effective.education.crossplatform.core.suspendRunCatching

/**
 * Реализация репозитория поверх сети: ответ PokéAPI разбирается из DTO в
 * доменные модели. [PokeApi] дан целиком, а вызов и разбор написаны
 * здесь — это и есть тема пары.
 */
class NetworkPokemonRepository(
    private val api: PokeApi,
) : PokemonRepository {

    override fun getPokemons(offset: Int, limit: Int): Flow<Result<Pokemon>> = flow {
        // Список внутри flow, а не до него: провал списка — тоже ответ, а не
        // исключение, которое уронило бы viewModelScope.
        val page = api.fetchList(limit = limit, offset = offset)
            .getOrElse {
                emit(Result.failure(it))
                return@flow
            }
        // Список PokéAPI отдаёт только имя и ссылку — полную запись собираем
        // по каждой. Записи уходят на экран по одной, по мере готовности.
        page.results.forEach { item ->
            val pokemonId = idFromUrl(item.url)
            emit(fetchPokemon(pokemonId))
        }
    }

    override suspend fun getPokemon(id: Int): Result<Pokemon> = fetchPokemon(id)

    /** Полная запись покемона [id]: три запроса к PokéAPI, склеенные в один [Pokemon]. */
    private suspend fun fetchPokemon(id: Int): Result<Pokemon> = suspendRunCatching {
        coroutineScope {
            val detailAsync = async { api.fetchDetail(id) }    // GET pokemon/{id}
            val speciesAsync = async { api.fetchSpecies(id) }  // GET pokemon-species/{id}
            val detail = detailAsync.await().getOrThrow()
            val species = speciesAsync.await().getOrThrow()
            val evolutionChain = async {
                api.fetchEvolutionChain(idFromUrl(species.evolutionChain.url))
            }.await().getOrThrow()
            createPokemon(detail, species, evolutionChain.chain.flattenIds())
        }
    }
}
