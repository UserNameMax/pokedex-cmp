package band.effective.education.crossplatform.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesDto(
    val genera: List<GenusEntryDto>,
    @SerialName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryDto>,
    @SerialName("evolution_chain")
    val evolutionChain: EvolutionChainRefDto,
)

@Serializable
data class EvolutionChainRefDto(val url: String)

@Serializable
data class GenusEntryDto(val genus: String, val language: NamedRefDto)


@Serializable
data class FlavorTextEntryDto(
    @SerialName("flavor_text")
    val flavorText: String,
    val language: NamedRefDto,
)

fun List<GenusEntryDto>.englishGenus(): String? = firstOrNull { it.language.name == "en" }?.genus

fun List<FlavorTextEntryDto>.englishFlavorText(): String? =
    firstOrNull { it.language.name == "en" }
        // \n и \x0c (разрыв страницы) — артефакты формата flavor_text у старых версий игры.
        ?.flavorText?.replace('\n', ' ')?.replace('', ' ')?.replace(Regex(" +"), " ")?.trim()
