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
}
