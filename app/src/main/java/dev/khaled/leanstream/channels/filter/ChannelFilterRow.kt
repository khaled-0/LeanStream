package dev.khaled.leanstream.channels.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import dev.khaled.leanstream.channels.ChannelCategory
import dev.khaled.leanstream.playSoundEffectOnFocus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterRow(
    channelCategories: List<ChannelCategory>,
    currentSelection: ChannelCategory,
    onSelect: (ChannelCategory) -> Unit,
) {
    TvLazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(channelCategories) {
            FilterChip(
                onClick = { onSelect(it) },
                label = { Text(it.label ?: it.value) },
                selected = it == currentSelection,
                modifier = Modifier.playSoundEffectOnFocus()
            )
        }
    }
}
