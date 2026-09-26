package band.effective.education.crossplatform.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
@Composable
fun CardSurface(modifier: Modifier = Modifier,
                content: @Composable () -> Unit)
{
    Surface(
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        shadowElevation = 1.dp,
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface
    )
    {
        Box(modifier.padding(10.dp))
        {
            content()
        }
    }
}