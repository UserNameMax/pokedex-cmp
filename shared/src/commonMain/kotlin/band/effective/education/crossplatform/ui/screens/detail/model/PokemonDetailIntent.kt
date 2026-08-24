package band.effective.education.crossplatform.ui.screens.detail.model

/** Намерения экрана детали — та же однонаправленность, что и у списка. */
sealed interface PokemonDetailIntent {
    /** Нажатие на соседа по эволюции: открыть его деталь поверх текущей. */
    data class EvolutionStageClicked(val id: Int) : PokemonDetailIntent
}
