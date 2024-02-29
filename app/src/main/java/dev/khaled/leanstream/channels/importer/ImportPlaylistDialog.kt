package dev.khaled.leanstream.channels.importer

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.channels.ChannelViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalSerializationApi::class)
@Composable
fun ImportPlaylistDialog(
    onDismiss: (success: Boolean) -> Unit,
) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    var url by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val channels = remember { mutableStateListOf<Channel>() }
    val channelViewModel: ChannelViewModel = viewModel<ChannelViewModel>()

    AlertDialog(icon = { Icon(Icons.Default.LibraryAdd, contentDescription = null) },
        title = { Text(text = "Import Playlist") },
        onDismissRequest = { },
        text = {
            TextField(value = url, onValueChange = { url = it })
        },
        confirmButton = {
            if (loading) CircularProgressIndicator()
            else if (channels.isNotEmpty()) TextButton(onClick = {
                try {
                    loading = true
                    channelViewModel.savePlaylistToDisk(context, channels)
                    onDismiss(true)
                } catch (e: Exception) {
                    error = e.stackTraceToString()
                } finally {
                    loading = false
                }
            }) {
                Text("Import ${channels.size} Channels")
            }
            else TextButton(onClick = {
                try {
                    loading = true
                    channels.clear()
                    channels.addAll(channelViewModel.parsePlaylist(Uri.parse(url)))
                } catch (e: Exception) {
                    error = e.stackTraceToString()
                } finally {
                    loading = false
                }
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            if (!loading) TextButton(onClick = { onDismiss(false) }) {
                Text("Dismiss")
            }
        })
}
