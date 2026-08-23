package band.effective.education.crossplatform.ui

import androidx.compose.runtime.Composable
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.type_bug
import band.effective.education.crossplatform.resources.type_fire
import band.effective.education.crossplatform.resources.type_flying
import band.effective.education.crossplatform.resources.type_grass
import band.effective.education.crossplatform.resources.type_normal
import band.effective.education.crossplatform.resources.type_poison
import band.effective.education.crossplatform.resources.type_water
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Тип приезжает из данных строкой вроде "grass" — это идентификатор, а не подпись.
 * На экран идёт перевод из ресурсов, иначе интерфейс останется наполовину английским
 * даже после переключения языка.
 *
 * Список неполный: в PokéAPI восемнадцать типов, в первых двадцати записях
 * встречаются семь. Остальные добавляются, когда появится сеть.
 */
private val typeLabels: Map<String, StringResource> = mapOf(
    "bug" to Res.string.type_bug,
    "fire" to Res.string.type_fire,
    "flying" to Res.string.type_flying,
    "grass" to Res.string.type_grass,
    "normal" to Res.string.type_normal,
    "poison" to Res.string.type_poison,
    "water" to Res.string.type_water,
)

@Composable
fun typeLabel(type: String): String {
    val res = typeLabels[type] ?: return type
    return stringResource(res)
}

@Composable
fun typeLabels(types: List<String>): String {
    // joinToString не inline, и composable-вызов внутрь её лямбды не пропускается —
    // сначала переводим через inline-map, потом склеиваем
    val labels = types.map { typeLabel(it) }
    return labels.joinToString(TYPE_SEPARATOR)
}

private const val TYPE_SEPARATOR = " · "
