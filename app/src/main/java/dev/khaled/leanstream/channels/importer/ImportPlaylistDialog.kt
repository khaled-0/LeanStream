package dev.khaled.leanstream.channels.importer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.channels.ChannelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun ImportPlaylistDialog(
    onDismiss: (success: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }
    var loading by remember { mutableStateOf(false) }
    var url by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val channels = remember { mutableStateListOf<Channel>() }
    val channelViewModel: ChannelViewModel = viewModel<ChannelViewModel>()

    AlertDialog(icon = {
        Icon(
            Icons.Default.LibraryAdd,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }, title = { Text(text = "Import Playlist") }, onDismissRequest = { }, text = {
        OutlinedTextField(value = url,
            onValueChange = { url = it },
            placeholder = { Text("https://amongus.sus/playlist.m3u8") })

        if (error.isNotEmpty()) {
            Card({ }) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Error, null, tint = MaterialTheme.colorScheme.error)
                    Text(error)
                }
            }
        }
    }, confirmButton = {
        if (loading) CircularProgressIndicator()
        else if (channels.isNotEmpty()) TextButton(content = {
            Text("Import ${channels.size} Channels")
        }, onClick = {
            try {
                loading = true
                channelViewModel.savePlaylistToDisk(context, channels)
                onDismiss(true)
            } catch (e: Exception) {
                error = e.stackTraceToString()
            } finally {
                loading = false
            }
        })
        else TextButton(content = { Text("Confirm") }, onClick = {
            coroutineScope.launch {
                try {
                    loading = true
                    channels.clear()
                    channels.addAll(channelViewModel.parsePlaylist(url))
                } catch (e: Exception) {
                    error = e.stackTraceToString()
                } finally {
                    loading = false
                }
            }
        })
    }, dismissButton = {
        if (!loading) TextButton(content = { Text("Dismiss") }, onClick = { onDismiss(false) })
    })
}
