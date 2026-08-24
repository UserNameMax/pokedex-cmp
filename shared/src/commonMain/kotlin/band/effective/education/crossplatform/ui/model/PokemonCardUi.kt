package band.effective.education.crossplatform.ui.model

import band.effective.education.crossplatform.ui.spriteOf
import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.filterByName

/**
 * Карточка списка — ровно то, что на ней нарисовано, и уже в том виде,
 * в каком нарисовано: имя с заглавной, номер тремя цифрами.
 *
 * Экран ничего не форматирует и доменную модель не видит. Поменялся способ
 * показа — меняется маппер, а не экран и не домен.
 *
 * Спрайт пока из ресурсов: записи — моки, и рисунок есть у каждой. Откуда
 * брать картинку, решает [PokemonImageModel], а не экран.
 */
data class PokemonCardUi(
    val id: Int,
    val name: String,
    val number: String,
    val imageModel: PokemonImageModel,
    val types: List<PokemonTypeUi>,
)

fun Pokemon.toCardUi(): PokemonCardUi = PokemonCardUi(
    id = id,
    name = displayName(name),
    number = displayNumber(id),
    imageModel = PokemonImageModel.RImage(spriteOf(this)),
    types = types.map { it.toUi() },
)

fun List<Pokemon>.toCardsUi(): List<PokemonCardUi> = map { it.toCardUi() }

/** Фильтр и маппинг одним вызовом: ровно то, что нужно экрану списка. */
fun List<Pokemon>.toCardsUi(filter: String?): List<PokemonCardUi> =
    filterByName(filter).toCardsUi()
