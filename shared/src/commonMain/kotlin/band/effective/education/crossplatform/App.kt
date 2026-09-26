package band.effective.education.crossplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Country

import band.effective.education.crossplatform.data.get_countries
import band.effective.education.crossplatform.screen.CountryListScreen
import band.effective.education.crossplatform.ui.components.CountryCard
import band.effective.education.crossplatform.ui.components.CountryDetailed
import band.effective.education.crossplatform.ui.components.StatBlock
import band.effective.education.crossplatform.ui.model.CountryCardUI
import band.effective.education.crossplatform.ui.model.toCardsUI

@Composable
fun App() {
        MaterialTheme{
            Scaffold (modifier = Modifier.background(Color(0xFFFFFF)),
            topBar = {
                TopAppBar({Text(text="test")})
            }
            ) {
                CountryListScreen()
            }

        }
}
