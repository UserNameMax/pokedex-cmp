package band.effective.education.crossplatform.domain.model

/** Как прошла последняя загрузка страницы каталога. */
sealed interface LoadStatus {
    data object Loading : LoadStatus
    data object Loaded : LoadStatus
    data class Failed(val cause: Throwable) : LoadStatus
}
