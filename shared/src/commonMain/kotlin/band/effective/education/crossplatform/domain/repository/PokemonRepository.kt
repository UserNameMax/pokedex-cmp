package band.effective.education.crossplatform.domain.repository

import band.effective.education.crossplatform.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

/**
 * Чтение каталога — так, как его видит домен.
 *
 * Откуда записи берутся, домен не знает: реализация лежит в data-слое. Читать
 * так умеют и сеть, и локальное хранилище ([PokemonLocalRepository]), поэтому
 * тому, кто только читает, всё равно, какой из них ему подставили.
 *
 * Ответы сразу [Result] — ошибка часть ответа, а не исключение. Список приходит
 * не целиком, а записями по одной, поэтому [Flow].
 */
interface PokemonRepository {
    /**
     * Страница каталога полными записями — по одной, по мере загрузки.
     *
     * Пришло меньше [limit] записей — у этого источника записи кончились. Для
     * сети это конец каталога, для локального хранилища — только конец того,
     * что успели скачать: признак конца каталога берётся у сети.
     *
     * Не `suspend`: Flow холодный, работа начинается на `collect`, а не на вызове.
     */
    fun getPokemons(offset: Int, limit: Int): Flow<Result<Pokemon>>

    /** Записи нет у этого источника — `Result.failure` с [PokemonNotFound]. */
    suspend fun getPokemon(id: Int): Result<Pokemon>
}

/**
 * Записи нет у источника. Отдельный тип, чтобы промах локального хранилища
 * («ещё не скачали — иди в сеть») не путался с настоящим сбоем.
 */
class PokemonNotFound(val id: Int) : NoSuchElementException("Pokemon $id not found")

/**
 * Записи, отфильтрованные по имени. `null` или пустой фильтр — все записи:
 * «поиск не задан» и «показать всё» для экрана одно и то же.
 *
 * Фильтр работает по уже загруженному списку, а не через репозиторий: с сетью
 * каждый набранный символ иначе превращался бы в запрос.
 */
fun List<Pokemon>.filterByName(filter: String?): List<Pokemon> {
    val needle = filter?.trim().orEmpty()
    if (needle.isEmpty()) return this
    return filter { it.name.contains(needle, ignoreCase = true) }
}
