package band.effective.education.crossplatform.ui.screens.detail

/** Намерения экрана детали — та же однонаправленность, что и у списка. */
sealed interface PokemonDetailIntent {
    data class EvolutionStageClicked(val id: Int) : PokemonDetailIntent
}
