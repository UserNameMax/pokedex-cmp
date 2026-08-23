package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.ability_hidden
import band.effective.education.crossplatform.ui.model.AbilityUi
import org.jetbrains.compose.resources.stringResource

@Composable
fun abilityString(ability: AbilityUi): String {
    return buildString {
        append(ability.name)
        if (ability.hidden) {
            append(" · " + stringResource(Res.string.ability_hidden))
        }
    }
}

@Composable
fun AbilityColumn(abilities: List<AbilityUi>){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            abilities.forEach { ability ->
                Text(
                    text = abilityString(ability),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
