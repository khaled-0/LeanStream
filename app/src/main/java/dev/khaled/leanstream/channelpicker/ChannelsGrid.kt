package dev.khaled.leanstream.channelpicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage

val channelSize = 128.dp

@Composable
fun ChannelsGrid(items: List<String>) {
    TvLazyVerticalGrid(
        columns = TvGridCells.Adaptive(channelSize), // Number of columns
        verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between rows
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Spacing between columns
        contentPadding = PaddingValues(16.dp), // Padding around grid
    ) {
        items(items) {
            GridItem(it) // Composable for each item
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GridItem(item: String) {

    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(channelSize),
        border = CardDefaults.border(focusedBorder = Border(BorderStroke(2.dp, Color.Black))),
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
//            placeholder = painterResource(R.drawable.empty_card_bg),
        )
    }

}


