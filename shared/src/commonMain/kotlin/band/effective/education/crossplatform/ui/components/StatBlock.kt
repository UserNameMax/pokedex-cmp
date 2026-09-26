package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Country
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.Res.string
import band.effective.education.crossplatform.resources.country_capital
import band.effective.education.crossplatform.resources.country_code
import band.effective.education.crossplatform.resources.country_gdp
import band.effective.education.crossplatform.resources.country_popularity
import band.effective.education.crossplatform.resources.country_region
import band.effective.education.crossplatform.resources.people_unit

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatBlock(modifier: Modifier = Modifier,
              country: Country)
{
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        StatIndicator(
            statName = Res.string.country_code,
            value = country.code
        )
        StatIndicator(
            statName = Res.string.country_capital,
            value = country.capital
        )
        StatIndicator(
            statName = Res.string.country_popularity,
            value = country.popularity.formatThousand() + " ${stringResource(string.people_unit)}"
        )
        StatIndicator(
            statName = Res.string.country_region,
            value = country.region
        )
        StatIndicator(
            statName = Res.string.country_gdp,
            value = country.GDP.formatThousand() +"$"
        )


    }
}

@Composable
fun StatIndicator(
    statName: StringResource,
    value: String,
)
{
    Row {
        Text(text = stringResource(statName) + ":", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(10.dp))
        Text(text = value)
    }
}