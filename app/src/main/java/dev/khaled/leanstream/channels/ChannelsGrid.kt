package dev.khaled.leanstream.channels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage
import dev.khaled.leanstream.conditional
import dev.khaled.leanstream.isRunningOnTV
import dev.khaled.leanstream.playSoundEffectOnFocus

val channelItemSize = 128.dp

@Composable
fun ChannelsGrid(items: List<Channel>, onClick: (channel: Channel) -> Unit) {
    var lastFocusedChannel by rememberSaveable { mutableStateOf<Int?>(null) }

    TvLazyVerticalGrid(
        modifier = Modifier.fillMaxHeight(),
        columns = TvGridCells.Adaptive(channelItemSize),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items) { channel ->
            GridItem(channel, onClick = {
                onClick(it)
                lastFocusedChannel = channel.hashCode()
            }, hadFocusBeforeNavigation = lastFocusedChannel == channel.hashCode()) {
                if (it.hasFocus) lastFocusedChannel = null
            }
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GridItem(
    channel: Channel,
    onClick: (channel: Channel) -> Unit,
    hadFocusBeforeNavigation: Boolean,
    onFocusChanged: (FocusState) -> Unit,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val isTouchScreen = remember { !isRunningOnTV(context) }
    val showChannelTitle = isTouchScreen //TODO

    Card(
        onClick = { onClick.invoke(channel) },
        modifier = Modifier
            .size(channelItemSize)
            .playSoundEffectOnFocus()
            .conditional(!isTouchScreen) {
                onFocusChanged(onFocusChanged)
                focusRequester(focusRequester)
                conditional(hadFocusBeforeNavigation) {
                    onGloballyPositioned {
                        focusRequester.requestFocus()
                    }
                }
            }
            .conditional(isTouchScreen) {
                clip(RoundedCornerShape(8))
            },
        border = CardDefaults.border(
            focusedBorder = Border(
                BorderStroke(
                    3.5.dp, MaterialTheme.colorScheme.primary
                )
            )
        ),
        colors = CardDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.conditional(isTouchScreen) {
            clickable { onClick.invoke(channel) }
        }) {
            AsyncImage(
                model = channel.icon,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                error = rememberVectorPainter(Icons.Rounded.BrokenImage),
            )

            if (showChannelTitle) channel.title?.let {
                TitleOverlay(title = it)
            }
        }
    }
}


@Composable
private fun TitleOverlay(
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = title,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.22f)
                .align(Alignment.BottomStart)
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent, 1f to Color(0xDD000000)
                    )
                )
        )
    }
}

