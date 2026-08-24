package band.effective.education.crossplatform.data.network

/**
 * URL спрайта по номеру записи — статичный, предсказуемый путь того же
 * репозитория, на который ссылается `sprites.front_default` в ответе API.
 * Не требует отдельного запроса.
 */
fun spriteUrl(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
