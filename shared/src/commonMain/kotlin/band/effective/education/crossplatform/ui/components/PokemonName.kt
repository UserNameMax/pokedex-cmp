package band.effective.education.crossplatform.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonName(name: String) {
    Text(name, style = MaterialTheme.typography.headlineMedium)
}
