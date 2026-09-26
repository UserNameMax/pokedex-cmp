package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardNum(value: Int)
{
    Row(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(Color(0xFF10C300))
        .padding(horizontal = 4.dp, vertical = 0.dp)   )
    {
        Text(text = "#" + value.toString().padStart(3, '0'))
    }
}