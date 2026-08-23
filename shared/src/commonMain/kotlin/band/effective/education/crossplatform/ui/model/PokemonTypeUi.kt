package band.effective.education.crossplatform.ui.model

import androidx.compose.ui.graphics.Color
import band.effective.education.crossplatform.domain.PokemonType

/**
 * Тип так, как его рисуют: подпись и цвет, уже переведённый в `Color`.
 *
 * Цвет типа приходит из домена и тему не слушает. Различать это с цветами
 * темы — половина работы над темой. Вторая половина — не путать их:
 * подложка карточки цветом типа быть не может.
 */
data class PokemonTypeUi(val name: String, val color: Color)

fun PokemonType.toUi(): PokemonTypeUi = PokemonTypeUi(name = name, color = Color(color))
