package band.effective.education.crossplatform.list

/**
 * Намерения — всё, что пользователь может сделать с экраном.
 *
 * Экран не меняет состояние сам, он только сообщает о намерении. Менять — работа
 * [PokemonListViewModel]. Отсюда однонаправленность: вниз состояние, вверх намерения,
 * и никаких путей в обход.
 *
 * Сейчас намерение одно — открыть карточку. Остальные добавляются сюда же.
 */
sealed interface PokemonListIntent {
    data class CardClicked(val id: Int) : PokemonListIntent
}
