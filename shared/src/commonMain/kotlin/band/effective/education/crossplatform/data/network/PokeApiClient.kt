package band.effective.education.crossplatform.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Клиент дан целиком — настройка Ktor не тема этой пары. `Logging` на уровне
 * `INFO` печатает исходящий запрос в консоль, по нему проверяется чекпойнт 1.
 */
fun pokeApiHttpClient(
    baseUrl: String,
): HttpClient = HttpClient {
    defaultRequest {
        url(baseUrl)
    }
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    install(Logging) {
        // SIMPLE пишет через println. По умолчанию на JVM логгер идёт в SLF4J,
        // а провайдера SLF4J в desktop-сборке нет: запрос молча уходил в NOP.
        logger = Logger.SIMPLE
        level = LogLevel.INFO
    }
}
