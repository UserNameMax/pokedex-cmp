package band.effective.education.crossplatform.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @SerialName("base_experience")
    val baseExperience: Int,
    val types: List<PokemonTypeSlotDto>,
    val abilities: List<PokemonAbilitySlotDto>,
    val stats: List<PokemonStatSlotDto>,
)

@Serializable
data class PokemonTypeSlotDto(val type: NamedRefDto)

@Serializable
data class PokemonAbilitySlotDto(
    val ability: NamedRefDto,
    @SerialName("is_hidden")
    val isHidden: Boolean,
)

@Serializable
data class PokemonStatSlotDto(
    @SerialName("base_stat")
    val baseStat: Int,
    val stat: NamedRefDto,
)
