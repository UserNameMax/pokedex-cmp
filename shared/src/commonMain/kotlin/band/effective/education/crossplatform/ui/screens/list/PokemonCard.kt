package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.components.CardSurface
import band.effective.education.crossplatform.ui.components.PokemonBaseStats
import band.effective.education.crossplatform.ui.components.PokemonImage
import band.effective.education.crossplatform.ui.model.PokemonCardUi
import org.jetbrains.compose.resources.painterResource

@Composable
fun PokemonCard(pokemon: PokemonCardUi, modifier: Modifier = Modifier) {
    CardSurface(modifier) {
        Row {
            PokemonImage(
                painter = painterResource(pokemon.sprite),
                bgColor = pokemon.types.first().color.copy(alpha = 0.16f),
                size = 80.dp,
                pokemonSize = 64.dp
            )
            Spacer(Modifier.width(16.dp))
            PokemonBaseStats(
                name = pokemon.name,
                number = pokemon.number,
                types = pokemon.types
            )
        }
    }
}
