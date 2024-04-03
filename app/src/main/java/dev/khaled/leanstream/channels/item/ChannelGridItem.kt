package dev.khaled.leanstream.channels.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.conditional
import dev.khaled.leanstream.isRunningOnTV
import dev.khaled.leanstream.playSoundEffectOnFocus

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GridItem(
    channel: Channel,
    modifier: Modifier = Modifier,
    onClick: (channel: Channel) -> Unit,
    hadFocusBeforeNavigation: Boolean,
    onFocusChanged: (FocusState) -> Unit,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val isTouchScreen = remember { !isRunningOnTV(context) }
    val showChannelTitle = isTouchScreen || channel.icon.isNullOrBlank() //TODO

    Card(
        onClick = { onClick.invoke(channel) },
        modifier = modifier
            .playSoundEffectOnFocus()
            .onFocusChanged(onFocusChanged)
            .conditional(!isTouchScreen) { focusRequester(focusRequester) }
            .conditional(!isTouchScreen && hadFocusBeforeNavigation) {
                onGloballyPositioned { focusRequester.requestFocus() }
            }
            .conditional(isTouchScreen) { clip(RoundedCornerShape(8)) },
        border = CardDefaults.border(
            focusedBorder = Border(
                BorderStroke(
                    4.dp, MaterialTheme.colorScheme.primary
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

