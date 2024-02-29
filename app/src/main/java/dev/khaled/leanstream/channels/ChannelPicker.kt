package dev.khaled.leanstream.channels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.khaled.leanstream.channels.importer.ImportPlaylistPrompt

@Composable
fun ChannelPicker(openChannel: (channel: Channel) -> Unit) {
    val viewModel: ChannelViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(context) {
        viewModel.load(context)
    }

    if (viewModel.isLoading) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
            )
        }
    }

    if (viewModel.channels.isEmpty()) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImportPlaylistPrompt {
                viewModel.load(context)
            }
        }
    }

    ChannelsGrid(items = viewModel.channels) {
        openChannel(it)
    }
}
