package band.effective.education.crossplatform.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokeListPageDto(
    val count: Int,
    val next: String?,
    val results: List<PokeListItemDto>,
)

@Serializable
data class PokeListItemDto(
    val name: String,
    val url: String,
)
