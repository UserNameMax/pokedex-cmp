package band.effective.education.crossplatform.detail

/** Намерения экрана детали — та же однонаправленность, что и у списка. */
sealed interface PokemonDetailIntent {
    data class EvolutionStageClicked(val id: Int) : PokemonDetailIntent
}
