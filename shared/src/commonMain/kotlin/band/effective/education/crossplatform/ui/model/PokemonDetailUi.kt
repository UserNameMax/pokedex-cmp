package band.effective.education.crossplatform.ui.model

import androidx.compose.ui.graphics.Color
import band.effective.education.crossplatform.domain.Pokemon
import band.effective.education.crossplatform.ui.spriteOf
import org.jetbrains.compose.resources.DrawableResource

/**
 * Экран детали — всё, что на нём нарисовано, уже в том виде, в каком нарисовано.
 *
 * Рост и вес здесь в метрах и килограммах, а не в единицах API: перевод —
 * решение о показе, и принимает его маппер, а не экран.
 */
data class PokemonDetailUi(
    val id: Int,
    val name: String,
    val number: String,
    val genus: String,
    val sprite: DrawableResource,
    /** Цвет первого типа: им красятся подложка спрайта и полосы характеристик. */
    val accent: Color,
    val types: List<PokemonTypeUi>,
    val height: String,
    val weight: String,
    val baseExperience: String,
    val flavorText: String,
    val stats: List<StatUi>,
    val abilities: List<AbilityUi>,
    val evolution: List<EvolutionStageUi>,
)

/** [key] остаётся идентификатором из API: подпись к нему берётся из ресурсов на экране. */
data class StatUi(val key: String, val value: Int)

data class AbilityUi(val name: String, val hidden: Boolean)

/** Этап эволюции. [current] — сама открытая запись: она подсвечена и не кликается. */
data class EvolutionStageUi(
    val id: Int,
    val name: String,
    val sprite: DrawableResource,
    val accent: Color,
    val current: Boolean,
)

/** @param evolution записи цепочки эволюции по порядку, включая саму эту запись. */
fun Pokemon.toDetailUi(evolution: List<Pokemon>): PokemonDetailUi = PokemonDetailUi(
    id = id,
    name = displayName(name),
    number = displayNumber(id),
    genus = genus,
    sprite = spriteOf(this),
    accent = types.first().toUi().color,
    types = types.map { it.toUi() },
    height = (heightDm / 10.0).toString(),
    weight = (weightHg / 10.0).toString(),
    baseExperience = baseExperience.toString(),
    flavorText = flavorText,
    stats = stats.map { StatUi(it.key, it.value) },
    abilities = abilities.map { AbilityUi(displayName(it.name), it.hidden) },
    evolution = evolution.map { stage ->
        EvolutionStageUi(
            id = stage.id,
            name = displayName(stage.name),
            sprite = spriteOf(stage),
            accent = stage.types.first().toUi().color,
            current = stage.id == id,
        )
    },
)
