package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Country

@Composable
fun CountryDetailed(
    modifier: Modifier,
    country: Country
) {
    Column(modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        //title

        Text(text = country.name, style = MaterialTheme.typography.titleMedium)

        StatBlock(country = country)

    }
}