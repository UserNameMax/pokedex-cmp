package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.model.CountryCardUI


@Composable
fun CountryList(
    countries: List<CountryCardUI>,
    modifier: Modifier = Modifier,
    onCountryClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        countries.forEach { country ->
            CountryCard(
                country = country,
                onClick = { onCountryClick(country.id) },
            )
        }
    }
}
