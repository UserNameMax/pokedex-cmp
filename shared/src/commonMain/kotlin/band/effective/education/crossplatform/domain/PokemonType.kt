package band.effective.education.crossplatform.domain

/**
 * Тип записи: идентификатор из API (`grass`) и его цвет.
 *
 * Цвет лежит в домене, а не в теме, и это тот случай, когда так правильно.
 * Цвет травы не должен меняться от переключения темы: он часть данных, а не
 * оформления. Тему обязаны слушать поверхности и текст — то есть всё, что
 * описывает интерфейс, а не содержимое.
 *
 * [color] — ARGB числом, а не `Color` из Compose: домен не знает, чем его
 * будут рисовать. В `Color` его переводит UI-модель.
 */
data class PokemonType(val name: String, val color: Long) {
    companion object {
        fun of(name: String): PokemonType = PokemonType(name, typeColors[name] ?: UNKNOWN_TYPE_COLOR)
    }
}

private val typeColors: Map<String, Long> = mapOf(
    "bug" to 0xFF7A8B14,
    "fire" to 0xFFE25822,
    "flying" to 0xFF8E7BD8,
    "grass" to 0xFF4E9A2F,
    "normal" to 0xFF8A8A5E,
    "poison" to 0xFF8E3A8E,
    "water" to 0xFF4C74D9,
)

private const val UNKNOWN_TYPE_COLOR = 0xFF6E6E6E
