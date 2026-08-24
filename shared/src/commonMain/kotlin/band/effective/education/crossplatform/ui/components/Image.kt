package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.ui.model.PokemonImageModel
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource

@Composable
fun Image(
    image: PokemonImageModel,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    when (image) {
        is PokemonImageModel.RImage -> {
            Image(
                painter = painterResource(image.res),
                contentDescription = contentDescription,
                modifier = modifier,
            )
        }

        is PokemonImageModel.UrlImage -> {
            AsyncImage(
                model = image.url,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
    }
}
