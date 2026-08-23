package band.effective.education.crossplatform.domain

/**
 * Одна запись каталога — доменная модель.
 *
 * Здесь данные такими, какие они есть, а не такими, как их показывают: имя
 * строчными (`bulbasaur`), номер числом, рост в дециметрах, вес в гектограммах.
 * Как всё это выглядит на экране, решает UI-модель, а не этот класс.
 *
 * Поля названы так же, как в ответе PokéAPI (`/api/v2/pokemon/{id}` и
 * `/pokemon-species/{id}`), чтобы в В2 сеть легла сюда без переименований.
 */
data class Pokemon(
    val id: Int,
    val name: String,
    /** Вид: «Seed Pokémon». Приходит из species и только на английском. */
    val genus: String,
    val types: List<PokemonType>,
    val heightDm: Int,
    val weightHg: Int,
    val baseExperience: Int,
    val abilities: List<Ability>,
    val stats: List<Stat>,
    /**
     * Вся цепочка эволюции целиком, включая саму запись: у бульбазавра это 1, 2, 3.
     * Это и есть «связанные элементы» на экране детали.
     */
    val evolutionChain: List<Int>,
    val flavorText: String,
)

/** Способность. Скрытые в игре достаются редко — поэтому помечаются отдельно. */
data class Ability(val name: String, val hidden: Boolean)

/**
 * Базовая характеристика. [key] — идентификатор из API (`special-attack`),
 * подпись к нему берётся из ресурсов.
 */
data class Stat(val key: String, val value: Int)
