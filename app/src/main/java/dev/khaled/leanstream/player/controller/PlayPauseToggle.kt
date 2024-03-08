package dev.khaled.leanstream.player.controller

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayPauseToggle(
    modifier: Modifier = Modifier, isPlaying: Boolean, error: String?, onToggle: () -> Unit,
) {
    //TODO Show error message somehow??
    FilledTonalIconButton(modifier = modifier.size(80.dp), onClick = onToggle) {
        Icon(
            when {
                error != null -> Icons.Rounded.ErrorOutline
                isPlaying -> Icons.Rounded.Pause
                else -> Icons.Rounded.PlayArrow
            }, contentDescription = null, modifier = Modifier.size(80.dp)
        )
    }
}