package band.effective.education.crossplatform.domain.repository

import band.effective.education.crossplatform.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

/**
 * Локальное хранилище каталога: то же чтение, что у [PokemonRepository], плюс
 * запись и наблюдение. Отдельным расширением, а не общим интерфейсом: кто
 * только читает, зависит от [PokemonRepository] и методов записи не видит.
 *
 * Методы сразу `suspend`, хотя хранилище пока в памяти: когда на его место
 * встанет диск или localStorage, ни интерактор, ни экран не поменяются.
 */
interface PokemonLocalRepository : PokemonRepository {

    /** Все сохранённые записи. Flow, а не StateFlow: база или файл отдают именно поток. */
    fun observeAll(): Flow<List<Pokemon>>

    /**
     * Запись с тем же id заменяет старую на её месте, а не встаёт второй:
     * обновлённая модель с бэка не задваивается.
     */
    suspend fun upsert(pokemon: Pokemon)

    suspend fun clear()
}
