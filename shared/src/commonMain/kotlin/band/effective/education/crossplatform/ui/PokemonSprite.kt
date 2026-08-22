package band.effective.education.crossplatform.ui

import band.effective.education.crossplatform.data.Pokemon
import org.jetbrains.compose.resources.DrawableResource
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.*

/**
 * Спрайт по номеру записи.
 *
 * Картинки лежат в ресурсах, а не тянутся из сети: на воркшопе Wi-Fi в аудитории —
 * не то, на что стоит закладываться. В В2 эта функция заменяется загрузкой по URL.
 */
fun spriteOf(pokemon: Pokemon): DrawableResource = when (pokemon.id) {
    1 -> Res.drawable.pokemon_1
    2 -> Res.drawable.pokemon_2
    3 -> Res.drawable.pokemon_3
    4 -> Res.drawable.pokemon_4
    5 -> Res.drawable.pokemon_5
    6 -> Res.drawable.pokemon_6
    7 -> Res.drawable.pokemon_7
    8 -> Res.drawable.pokemon_8
    9 -> Res.drawable.pokemon_9
    10 -> Res.drawable.pokemon_10
    11 -> Res.drawable.pokemon_11
    12 -> Res.drawable.pokemon_12
    13 -> Res.drawable.pokemon_13
    14 -> Res.drawable.pokemon_14
    15 -> Res.drawable.pokemon_15
    16 -> Res.drawable.pokemon_16
    17 -> Res.drawable.pokemon_17
    18 -> Res.drawable.pokemon_18
    19 -> Res.drawable.pokemon_19
    20 -> Res.drawable.pokemon_20
    else -> error("Спрайта для записи ${pokemon.id} нет в ресурсах")
}
