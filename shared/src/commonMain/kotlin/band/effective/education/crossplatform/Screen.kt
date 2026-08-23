package band.effective.education.crossplatform

/**
 * Экраны приложения. В Navigation 3 маршрут — это обычный объект, а не строка:
 * аргумент лежит полем, и опечататься в нём нельзя, компилятор не даст.
 */
sealed interface Screen {
    data object List : Screen
    data class Detail(val id: Int) : Screen
}
