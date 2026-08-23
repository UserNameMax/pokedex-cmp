package band.effective.education.crossplatform.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.pokemon_number
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonNumber(number: String) {
    Text(
        text = stringResource(Res.string.pokemon_number, number),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
