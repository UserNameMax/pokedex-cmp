package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.attach_money
import band.effective.education.crossplatform.resources.emoji_people
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.ui.model.CountryCardUI
import org.jetbrains.compose.resources.painterResource

@Composable
fun CountryCard(modifier: Modifier = Modifier,
                country: CountryCardUI)
{
    CardSurface(modifier = Modifier.fillMaxWidth())
    {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp))
        {
            //шапка карточки
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp))
            {
                CardNum(country.id)
                Text(text = country.code)
                Text(text= country.name)
            }
            // низ какрточки
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,)
            {
                CardStat(
                    icon = painterResource(Res.drawable.emoji_people),
                    value = country.popularity,
                )
                CardStat(
                    icon = painterResource(Res.drawable.attach_money),
                    value = country.GDP,)
            }
        }
    }
}