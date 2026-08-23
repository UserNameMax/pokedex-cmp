package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.model.PokemonTypeUi

@Composable
fun TypeRow(types: List<PokemonTypeUi>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        types.forEach { type ->
            TypeChip(label = type.name, color = type.color)
        }
    }
}
