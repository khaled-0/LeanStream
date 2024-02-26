package dev.khaled.leanstream.channels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage

val channelItemSize = 128.dp

@Composable
fun ChannelsGrid(items: List<String>, onClick: (item: String) -> Unit) {
    TvLazyVerticalGrid(
        columns = TvGridCells.Adaptive(channelItemSize), // Number of columns
        verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between rows
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Spacing between columns
        contentPadding = PaddingValues(16.dp), // Padding around grid
    ) {
        items(items) {
            GridItem(it, onClick) // Composable for each item
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GridItem(item: String, onClick: (item: String) -> Unit) {
    Card(
        onClick = { onClick.invoke(item) },
        modifier = Modifier.size(channelItemSize),
        border = CardDefaults.border(focusedBorder = Border(BorderStroke(2.dp, Color.Black))),
    ) {
        AsyncImage(
            model = item,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            // contentScale = ContentScale.Inside,
            error = rememberVectorPainter(Icons.Rounded.BrokenImage),
        )
    }
}

