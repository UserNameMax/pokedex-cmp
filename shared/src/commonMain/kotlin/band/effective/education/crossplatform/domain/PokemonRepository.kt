package band.effective.education.crossplatform.domain

/**
 * Доступ к записям каталога — так, как его видит домен.
 *
 * Откуда записи берутся, домен не знает: реализация лежит в data-слое. Методы
 * сразу `suspend`, хотя на моках ответ мгновенный: когда на их место встанет
 * сеть, интерфейс и все, кто его вызывает, останутся как есть.
 */
interface PokemonRepository {
    suspend fun getPokemons(): List<Pokemon>
    suspend fun getPokemon(id: Int): Pokemon
}

/**
 * Записи, отфильтрованные по имени. `null` или пустой фильтр — все записи:
 * «поиск не задан» и «показать всё» для экрана одно и то же.
 */
suspend fun PokemonRepository.getPokemons(filter: String?): List<Pokemon> =
    getPokemons().filterByName(filter)

fun List<Pokemon>.filterByName(filter: String?): List<Pokemon> {
    val needle = filter?.trim().orEmpty()
    if (needle.isEmpty()) return this
    return filter { it.name.contains(needle, ignoreCase = true) }
}
