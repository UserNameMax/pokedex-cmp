package band.effective.education.crossplatform.core

import kotlinx.coroutines.CancellationException

/**
 * `runCatching`, который не глотает отмену. Обычный `runCatching` ловит и
 * [CancellationException] — тогда ушедший с экрана запрос вернулся бы как
 * «ошибка сети», а корутина продолжила бы работать после отмены.
 *
 * Throwable, а не Exception: сетевой сбой в Ktor JS-движке приходит как
 * kotlin.Error ("Fail to fetch"), а Error не наследует Exception.
 *
 * `inline` — чтобы внутри [block] можно было звать `suspend`-функции.
 */
internal inline fun <T> suspendRunCatching(block: () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
