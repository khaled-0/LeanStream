package dev.khaled.leanstream.channels.importer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.khaled.leanstream.playSoundEffectOnFocus

@Composable
fun ImportPlaylistPrompt(result: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.VideoLibrary, modifier = Modifier.size(64.dp), contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "No entries found. Import a playlist (.m3u8) below", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { showDialog = true }, modifier = Modifier.playSoundEffectOnFocus()
        ) {
            Text(text = "Import")
        }
    }

    when {
        showDialog -> {
            ImportPlaylistDialog(onDismiss = {
                showDialog = false
                if (it) result()
            })
        }
    }
}


