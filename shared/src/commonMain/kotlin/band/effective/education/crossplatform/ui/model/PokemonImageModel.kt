package band.effective.education.crossplatform.ui.model

import org.jetbrains.compose.resources.DrawableResource

sealed interface PokemonImageModel {
    data class RImage(val res: DrawableResource) : PokemonImageModel
    data class UrlImage(val url: String) : PokemonImageModel
}
