package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.detail_base_xp
import band.effective.education.crossplatform.resources.detail_height
import band.effective.education.crossplatform.resources.detail_weight
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonFactRow(
    height: String,
    weight: String,
    baseExperience: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Fact(stringResource(Res.string.detail_height, height))
        Fact(stringResource(Res.string.detail_weight, weight))
        Fact(stringResource(Res.string.detail_base_xp, baseExperience))
    }
}
