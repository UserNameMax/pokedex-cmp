package band.effective.education.crossplatform.ui

import androidx.compose.runtime.Composable
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.stat_attack
import band.effective.education.crossplatform.resources.stat_defense
import band.effective.education.crossplatform.resources.stat_hp
import band.effective.education.crossplatform.resources.stat_special_attack
import band.effective.education.crossplatform.resources.stat_special_defense
import band.effective.education.crossplatform.resources.stat_speed
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Ключ характеристики приходит из API идентификатором: `special-attack`.
 * Подпись к нему — из ресурсов, ключей ровно шесть и других не бывает.
 */
private val statLabels: Map<String, StringResource> = mapOf(
    "hp" to Res.string.stat_hp,
    "attack" to Res.string.stat_attack,
    "defense" to Res.string.stat_defense,
    "special-attack" to Res.string.stat_special_attack,
    "special-defense" to Res.string.stat_special_defense,
    "speed" to Res.string.stat_speed,
)

@Composable
fun statLabel(key: String): String {
    val res = statLabels[key] ?: return key
    return stringResource(res)
}
