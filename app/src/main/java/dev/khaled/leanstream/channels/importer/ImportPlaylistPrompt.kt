package dev.khaled.leanstream.channels.importer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import dev.khaled.leanstream.playSoundEffectOnFocus

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ImportPlaylistPrompt(result: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.VideoLibrary,
            modifier = Modifier.size(64.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "No entries found. Import a playlist (.m3u8) below")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }, modifier = Modifier.playSoundEffectOnFocus()) {
            Text(text = "Import")
        }
    }

    when {
        showDialog -> {
            ImportPlaylistDialog(
                onDismiss = {
                    showDialog = false
                    if (it) result()
                }
            )
        }
    }
}


