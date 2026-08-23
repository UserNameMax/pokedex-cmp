package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.model.EvolutionStageUi

@Composable
fun EvolutionRow(
    evolution: List<EvolutionStageUi>,
    onClick: (Int) -> Unit
){
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        evolution.forEach { stage ->
            EvolutionStage(
                stage = stage,
                onClick = { onClick(stage.id) },
            )
        }
    }
}
