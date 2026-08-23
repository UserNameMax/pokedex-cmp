package band.effective.education.crossplatform.list

/**
 * Намерения — всё, что пользователь может сделать с экраном.
 *
 * Экран не меняет состояние сам, он только сообщает о намерении. Менять — работа
 * [PokemonListViewModel]. Отсюда однонаправленность: вниз состояние, вверх намерения,
 * и никаких путей в обход.
 *
 * Каждое действие пользователя — отдельный вариант этого интерфейса.
 */
sealed interface PokemonListIntent {
    data class CardClicked(val id: Int) : PokemonListIntent
    data class QueryChanged(val value: String) : PokemonListIntent
}
