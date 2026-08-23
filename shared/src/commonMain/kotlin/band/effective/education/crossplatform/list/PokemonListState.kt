package band.effective.education.crossplatform.list

import band.effective.education.crossplatform.ui.model.PokemonCardUi

/**
 * Состояние экрана списка — **всё**, что нужно нарисовать, одним объектом.
 *
 * Правило, ради которого это так: экран не спрашивает ни у кого «а как там дела».
 * Он получает состояние и рисует его. Если чего-то на экране не видно в этом
 * классе — значит оно нарисовано мимо архитектуры.
 */
data class PokemonListState(
    val items: List<PokemonCardUi> = emptyList(),
)
