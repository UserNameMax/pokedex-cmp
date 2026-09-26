package band.effective.education.crossplatform

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import band.effective.education.crossplatform.navigation.AppRoute
import band.effective.education.crossplatform.navigation.AppViewModel
import band.effective.education.crossplatform.screen.CountryDetailScreen
import band.effective.education.crossplatform.screen.CountryListScreen
import band.effective.education.crossplatform.ui.AppScaffold

@Composable
fun App() {
    MaterialTheme {
        val viewModel: AppViewModel = viewModel { AppViewModel() }
        val backStack by viewModel.backStack.collectAsState()
        val current = backStack.last()

        AppScaffold(
            onBack = if (backStack.size > 1) {
                { viewModel.onBack() }
            } else {
                null
            },
        ) {
            when (val route = current) {
                is AppRoute.CountryList -> CountryListScreen(
                    onCountryClick = viewModel::onCountryClick,
                )
                is AppRoute.CountryDetail -> CountryDetailScreen(
                    countryId = route.countryId,
                    onCountryClick = viewModel::onCountryClick
                )
            }
        }
    }
}
