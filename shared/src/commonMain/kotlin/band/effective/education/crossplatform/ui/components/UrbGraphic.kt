package band.effective.education.crossplatform.ui.components

import Bar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Country
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.country_neighbours
import band.effective.education.crossplatform.resources.country_rur
import band.effective.education.crossplatform.resources.country_urb
import org.jetbrains.compose.resources.stringResource

private val UrbColor = Color(0xFF10C300)
private val RurColor = Color(0xFF4A90E2)

@Composable
fun UrbGraphic(
    country: Country,
    modifier: Modifier = Modifier,

) {
    val maxHeight: Int = 600
    val minHeight: Int = 100
    val urbHeight = (maxHeight * country.urb_tot / 100f).toInt().coerceIn(minHeight,maxHeight)
    val rurHeight = (maxHeight * country.rur_tot / 100f).toInt().coerceIn(minHeight,maxHeight)
    Column {
        Text(text = stringResource(Res.string.country_neighbours) + ":")
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Bar(
                value = country.urb_tot,
                height = urbHeight,
                color = UrbColor,
                name = stringResource(Res.string.country_urb)
            )
            Bar(
                value = country.rur_tot,
                height = rurHeight,
                color = RurColor,
                name = stringResource(Res.string.country_rur)
            )
        }
    }

}