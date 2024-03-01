package dev.khaled.leanstream.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text


@OptIn(ExperimentalTvMaterial3Api::class)
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

        Text(
            text = "Stream", style = TextStyle.Default.copy(
                fontSize = 30.sp, drawStyle = Stroke(
                    miter = 10f,
                    width = 1.5f,
                )
            )
        )
    }
}