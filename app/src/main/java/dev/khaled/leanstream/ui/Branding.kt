package dev.khaled.leanstream.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.khaled.leanstream.R


@Composable
fun Branding() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Lean", style = TextStyle.Default.copy(
                fontSize = 30.sp, color = MaterialTheme.colorScheme.primary
            )
        )

        Icon(
            painter = painterResource(id = R.drawable.round_stream_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
    }
}