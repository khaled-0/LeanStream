package dev.khaled.leanstream.player.controller

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import coil.compose.AsyncImage

@Composable
fun ChannelInfo(metadata: MediaMetadata) {
    Card {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = metadata.displayTitle.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = metadata.artworkUri,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                error = rememberVectorPainter(Icons.Rounded.BrokenImage),
            )
        }
    }
}