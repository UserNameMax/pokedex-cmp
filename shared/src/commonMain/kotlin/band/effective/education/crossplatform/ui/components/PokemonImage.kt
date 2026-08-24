package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import band.effective.education.crossplatform.ui.model.PokemonImageModel


/** Спрайт по сети на круглой подложке: свой рисунок есть только у первых двадцати записей. */
@Composable
fun PokemonImage(
    imageModel: PokemonImageModel,
    bgColor: Color,
    size: Dp,
    pokemonSize: Dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bgColor),
    ) {
        Image(
            image = imageModel,
            contentDescription = null,
            modifier = Modifier.size(pokemonSize)
        )
    }
}
