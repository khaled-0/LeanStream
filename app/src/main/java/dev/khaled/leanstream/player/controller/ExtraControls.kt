package dev.khaled.leanstream.player.controller

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun ExtraControls(
    backHandler: () -> Unit,
//    toggleFullScreen: () -> Unit,
) {
    Row {
        FilledTonalIconButton(onClick = backHandler) {
            Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
        }
//        FilledTonalIconButton(onClick = toggleFullScreen) {
//            Icon(Icons.Rounded.Fullscreen, contentDescription = null)
//        }
    }
}

