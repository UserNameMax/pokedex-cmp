package band.effective.education.crossplatform.data.network

import band.effective.education.crossplatform.core.suspendRunCatching
import band.effective.education.crossplatform.data.network.dto.EvolutionChainDto
import band.effective.education.crossplatform.data.network.dto.PokeListPageDto
import band.effective.education.crossplatform.data.network.dto.PokemonDetailDto
import band.effective.education.crossplatform.data.network.dto.PokemonSpeciesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Три метода, которыми пользуется весь воркшоп. Сигнатуры даны, реализация —
 * тоже: это обвязка. Тема пары — то, что вызывает эти методы и как экран
 * реагирует на результат.
 *
 * Пути относительные, без ведущего `/`: базовый адрес задан в клиенте
 * ([pokeApiHttpClient]), и `/pokemon` отрезал бы от него `api/v2`.
 */
class PokeApi(private val client: HttpClient) {

    suspend fun fetchList(limit: Int, offset: Int): Result<PokeListPageDto> = suspendRunCatching {
        client.get("pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }

    suspend fun fetchDetail(id: Int): Result<PokemonDetailDto> = suspendRunCatching {
        client.get("pokemon/$id").body()
    }

    suspend fun fetchSpecies(id: Int): Result<PokemonSpeciesDto> = suspendRunCatching {
        client.get("pokemon-species/$id").body()
    }

    suspend fun fetchEvolutionChain(id: Int): Result<EvolutionChainDto> = suspendRunCatching {
        client.get("evolution-chain/$id").body()
    }
}
