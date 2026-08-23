package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.model.EvolutionStageUi
import org.jetbrains.compose.resources.painterResource

@Composable
fun EvolutionStage(stage: EvolutionStageUi, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(96.dp).clickable(enabled = !stage.current, onClick = onClick),
    ) {
        PokemonImage(
            painter = painterResource(stage.sprite),
            bgColor = stage.accent.copy(alpha = if (stage.current) 0.30f else 0.12f),
            size = 72.dp,
            pokemonSize = 56.dp
        )
        EvolutionStageName(stage.name, stage.current)
    }
}
