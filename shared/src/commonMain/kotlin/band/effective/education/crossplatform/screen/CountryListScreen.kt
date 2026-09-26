package band.effective.education.crossplatform.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.data.get_countries
import band.effective.education.crossplatform.ui.components.CountryList
import band.effective.education.crossplatform.ui.model.toCardsUI

@Composable
fun CountryListScreen(
    onCountryClick: (Int) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val countries = remember { get_countries().toCardsUI() }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        // здесь будет еще строка поиска
        CountryList(
            countries = countries,
            onCountryClick = onCountryClick,
        )
    }
}
