package dev.khaled.leanstream.channels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Border
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.FilterChip
import androidx.tv.material3.FilterChipDefaults
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CategoryFilterRow(
    channelCategories: List<ChannelCategory>,
    currentSelection: ChannelCategory,
    onSelect: (ChannelCategory) -> Unit,
) {
    TvLazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        items(channelCategories) {
            FilterChip(
                onClick = { onSelect(it) },
                content = { Text(it.label ?: it.value) },
                selected = it == currentSelection,
                shape = FilterChipDefaults.shape(
                    FilterChipDefaults.ContainerShape.copy(CornerSize(100)),
                ),
                border = FilterChipDefaults.border(
                    border = Border.None,
                    selectedBorder = Border.None,
                    focusedSelectedBorder = Border.None
                )
            )
        }
    }
}
