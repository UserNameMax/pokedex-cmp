package band.effective.education.crossplatform.ui

import androidx.compose.ui.graphics.Color

/**
 * Цвет типа. Числовой — и это тот случай, когда так правильно.
 *
 * Цвет травы не должен меняться от переключения темы: он часть данных, а не
 * оформления. Тему обязаны слушать поверхности и текст — то есть всё, что
 * описывает интерфейс, а не содержимое.
 *
 * Различать эти два случая — половина работы над темой. Вторая половина —
 * не путать их: подложка карточки цветом типа быть не может.
 */
private val typeColors: Map<String, Color> = mapOf(
    "bug" to Color(0xFF7A8B14),
    "fire" to Color(0xFFE25822),
    "flying" to Color(0xFF8E7BD8),
    "grass" to Color(0xFF4E9A2F),
    "normal" to Color(0xFF8A8A5E),
    "poison" to Color(0xFF8E3A8E),
    "water" to Color(0xFF4C74D9),
)

private val unknownTypeColor = Color(0xFF6E6E6E)

fun typeColor(type: String): Color = typeColors[type] ?: unknownTypeColor
