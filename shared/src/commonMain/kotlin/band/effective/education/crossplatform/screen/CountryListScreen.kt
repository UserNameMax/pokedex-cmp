package band.effective.education.crossplatform.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.get_countries
import band.effective.education.crossplatform.ui.components.CountryCard
import band.effective.education.crossplatform.ui.components.CountryList
import band.effective.education.crossplatform.ui.model.toCardsUI

@Composable
fun CountryListScreen(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val countries = remember { get_countries().toCardsUI() }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // здесь будет строка поиска
        CountryList(countries)
    }
}
