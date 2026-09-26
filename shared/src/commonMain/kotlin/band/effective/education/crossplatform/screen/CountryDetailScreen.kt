package band.effective.education.crossplatform.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.data.get_countries
import band.effective.education.crossplatform.ui.components.CountryDetailed

@Composable
fun CountryDetailScreen(
    countryId: Int,
    onCountryClick: (Int) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val country = remember(countryId) {
        get_countries().first { it.id == countryId }
    }

    CountryDetailed(
        country = country,
        modifier = modifier.verticalScroll(rememberScrollState()),
        onCountryClick = onCountryClick
    )
}
