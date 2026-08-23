package band.effective.education.crossplatform.ui.navigation

import band.effective.education.crossplatform.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Бэкстек приложения. В Navigation 3 он — обычный список, и он ваш: никакого
 * графа маршрутов нет, открыть экран значит добавить объект в конец списка,
 * вернуться назад — убрать последний.
 *
 * Список живёт здесь, а не в `App`: ViewModel зовут [addToBackStack] и не
 * знают ни про NavDisplay, ни про то, как бэкстек нарисован.
 */
class Navigator {
    private val _navStack: MutableStateFlow<List<Screen>> = MutableStateFlow(listOf(Screen.List))
    val navStack = _navStack.asStateFlow()

    fun addToBackStack(screen: Screen){
        _navStack.update { it + screen }
    }

    fun back(){
        _navStack.update { currentStack ->
            if (currentStack.size > 1) {
                currentStack.dropLast(1)
            } else {
                currentStack
            }
        }
    }
}
