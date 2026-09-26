package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Country
import band.effective.education.crossplatform.data.get_neighbours
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.country_neighbours
import org.jetbrains.compose.resources.stringResource

@Composable
fun CountryDetailed(
    modifier: Modifier,
    country: Country,
    onCountryClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(text = country.name, style = MaterialTheme.typography.titleLarge)

        StatBlock(country = country)
        UrbGraphic(country = country)
        Column {
            Text(text = stringResource(Res.string.country_neighbours) + ":")
            Spacer(Modifier.height(16.dp))
            CountryList(
                countries = get_neighbours(country = country),
                onCountryClick = onCountryClick
            )
        }
    }
}