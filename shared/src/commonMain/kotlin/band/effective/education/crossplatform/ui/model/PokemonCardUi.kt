package band.effective.education.crossplatform.ui.model

import band.effective.education.crossplatform.domain.Pokemon
import band.effective.education.crossplatform.ui.spriteOf
import org.jetbrains.compose.resources.DrawableResource

/**
 * Карточка списка — ровно то, что на ней нарисовано, и уже в том виде,
 * в каком нарисовано: имя с заглавной, номер тремя цифрами.
 *
 * Экран ничего не форматирует и доменную модель не видит. Поменялся способ
 * показа — меняется маппер, а не экран и не домен.
 */
data class PokemonCardUi(
    val id: Int,
    val name: String,
    val number: String,
    val sprite: DrawableResource,
    val types: List<PokemonTypeUi>,
)

fun Pokemon.toCardUi(): PokemonCardUi = PokemonCardUi(
    id = id,
    name = displayName(name),
    number = displayNumber(id),
    sprite = spriteOf(this),
    types = types.map { it.toUi() },
)

fun List<Pokemon>.toCardsUi(): List<PokemonCardUi> = map { it.toCardUi() }
