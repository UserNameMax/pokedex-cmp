package band.effective.education.crossplatform.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import band.effective.education.crossplatform.ui.model.StatUi
import band.effective.education.crossplatform.ui.statLabel

@Composable
fun PokemonStats(
    stats: List<StatUi>,
    accent: Color,
) {
    stats.forEach { stat ->
        StatBar(label = statLabel(stat.key), value = stat.value, color = accent)
    }
}
