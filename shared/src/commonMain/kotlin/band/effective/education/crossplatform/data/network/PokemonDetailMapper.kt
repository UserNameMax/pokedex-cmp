package band.effective.education.crossplatform.data.network

import band.effective.education.crossplatform.data.network.dto.PokemonDetailDto
import band.effective.education.crossplatform.data.network.dto.PokemonSpeciesDto
import band.effective.education.crossplatform.data.network.dto.englishFlavorText
import band.effective.education.crossplatform.data.network.dto.englishGenus
import band.effective.education.crossplatform.domain.model.Ability
import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.model.PokemonType
import band.effective.education.crossplatform.domain.model.Stat

/**
 * Три ответа сети → одна доменная запись. Дано целиком — склейка полей не тема
 * пары, тема пары в том, кто и когда эти три запроса вызывает.
 */
fun createPokemon(
    detail: PokemonDetailDto,
    species: PokemonSpeciesDto,
    evolutionChain: List<Int>,
): Pokemon = Pokemon(
    id = detail.id,
    name = detail.name,
    genus = species.genera.englishGenus() ?: "",
    types = detail.types.map { PokemonType.of(it.type.name) },
    heightDm = detail.height,
    weightHg = detail.weight,
    baseExperience = detail.baseExperience,
    abilities = detail.abilities.map { Ability(it.ability.name, it.isHidden) },
    stats = detail.stats.map { Stat(it.stat.name, it.baseStat) },
    evolutionChain = evolutionChain,
    flavorText = species.flavorTextEntries.englishFlavorText() ?: "",
)
