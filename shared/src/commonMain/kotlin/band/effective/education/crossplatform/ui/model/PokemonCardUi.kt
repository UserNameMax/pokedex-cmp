package band.effective.education.crossplatform.ui.model

import band.effective.education.crossplatform.domain.model.Pokemon
import band.effective.education.crossplatform.domain.repository.filterByName
import band.effective.education.crossplatform.data.network.spriteUrl

/**
 * Карточка списка — ровно то, что на ней нарисовано, и уже в том виде,
 * в каком нарисовано: имя с заглавной, номер тремя цифрами.
 *
 * Экран ничего не форматирует и доменную модель не видит. Поменялся способ
 * показа — меняется маппер, а не экран и не домен.
 *
 * Спрайт — по URL: свой рисунок в ресурсах есть только у первых двадцати
 * записей. Откуда брать картинку, решает [PokemonImageModel], а не экран.
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
    imageModel = PokemonImageModel.UrlImage(spriteUrl(id)),
    types = types.map { it.toUi() },
)

fun List<Pokemon>.toCardsUi(): List<PokemonCardUi> = map { it.toCardUi() }

/** Фильтр и маппинг одним вызовом: ровно то, что нужно экрану списка. */
fun List<Pokemon>.toCardsUi(filter: String?): List<PokemonCardUi> =
    filterByName(filter).toCardsUi()
