package band.effective.education.crossplatform.ui.screens.detail.model

import band.effective.education.crossplatform.ui.model.PokemonDetailUi

/**
 * Состояние экрана детали — сумма, как у списка. Пустого состояния у детали
 * нет: запись либо загрузилась, либо нет — и тогда это ошибка с «Повторить».
 */
sealed interface PokemonDetailState {

    data object Loading : PokemonDetailState

    data class Error(val message: String) : PokemonDetailState

    data class Success(val pokemon: PokemonDetailUi) : PokemonDetailState
}
