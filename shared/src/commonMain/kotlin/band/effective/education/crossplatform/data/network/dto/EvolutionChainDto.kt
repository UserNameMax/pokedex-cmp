package band.effective.education.crossplatform.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainDto(val chain: ChainLinkDto)

@Serializable
data class ChainLinkDto(
    val species: NamedRefDto,
    @SerialName("evolves_to")
    val evolvesTo: List<ChainLinkDto>,
)

/** Разворачивает дерево эволюции в плоский список id, по одному на стадию. */
fun ChainLinkDto.flattenIds(): List<Int> =
    listOf(idFromUrl(species.url)) + evolvesTo.flatMap { it.flattenIds() }

fun idFromUrl(url: String): Int =
    url.trimEnd('/').substringAfterLast('/').toInt()
