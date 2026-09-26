package band.effective.education.crossplatform.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _backStack = MutableStateFlow<List<AppRoute>>(listOf(AppRoute.CountryList))
    val backStack: StateFlow<List<AppRoute>> = _backStack.asStateFlow()

    val current: AppRoute
        get() = _backStack.value.last()

    val canGoBack: Boolean
        get() = _backStack.value.size > 1

    fun onCountryClick(countryId: Int) {
        _backStack.update { stack -> stack + AppRoute.CountryDetail(countryId) }
    }

    fun onBack() {
        _backStack.update { stack ->
            if (stack.size > 1) stack.dropLast(1) else stack
        }
    }
}
