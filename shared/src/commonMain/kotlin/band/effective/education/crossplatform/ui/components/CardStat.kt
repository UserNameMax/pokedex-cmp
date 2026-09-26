package band.effective.education.crossplatform.ui.components
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CardStat(
    icon: Painter,
    value: Number,
)
{
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Icon(
            painter = icon,
            contentDescription = null
        )
        Spacer(Modifier.width(1.dp))
        Text(text = value.formatThousand())
    }
}

fun Number.formatThousand(separator: Char = ' '): String
{
    val s = this.toString()
    val formatted = s.reversed().chunked(3).joinToString(separator.toString())
        .reversed()

    return formatted
}