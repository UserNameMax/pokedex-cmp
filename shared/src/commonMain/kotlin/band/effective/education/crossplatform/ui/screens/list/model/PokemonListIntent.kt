package band.effective.education.crossplatform.ui.screens.list.model

/**
 * Намерения — всё, что пользователь может сделать с экраном.
 *
 * Экран не меняет состояние сам, он только сообщает о намерении. Менять — работа
 * [band.effective.education.crossplatform.ui.screens.list.view_model.PokemonListViewModel]. Отсюда однонаправленность: вниз состояние, вверх намерения,
 * и никаких путей в обход.
 *
 * Каждое действие пользователя — отдельный вариант этого интерфейса.
 */
sealed interface PokemonListIntent {
    data class CardClicked(val id: Int) : PokemonListIntent
    data class QueryChanged(val value: String) : PokemonListIntent

    /** Экран прокрутили близко к концу. Сам список не грузит — только сообщает. */
    data object LoadMoreRequested : PokemonListIntent

    /** Кнопка «Повторить» на экране ошибки. */
    data object RetryRequested : PokemonListIntent
}
