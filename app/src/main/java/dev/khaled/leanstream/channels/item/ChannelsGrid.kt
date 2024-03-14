package dev.khaled.leanstream.channels.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.khaled.leanstream.channels.Channel


@Composable
fun ChannelsGrid(items: List<Channel>, onClick: (channel: Channel) -> Unit) {
    val channelItemSize =
        when (LocalContext.current.resources.displayMetrics.densityDpi) {
            in 250..999 -> 128.dp
            else -> 150.dp
        }

    var lastFocusedChannel by rememberSaveable { mutableStateOf<Int?>(null) }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxHeight(),
        columns = GridCells.Adaptive(channelItemSize),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items) { channel ->
            GridItem(
                channel, Modifier.size(channelItemSize), onClick = {
                    onClick(it)
                    lastFocusedChannel = channel.hashCode()
                }, hadFocusBeforeNavigation = lastFocusedChannel == channel.hashCode()
            ) {
                if (it.hasFocus) lastFocusedChannel = null
            }
        }
    }
}


