package dev.khaled.leanstream.channels.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import dev.khaled.leanstream.channels.ChannelCategory
import dev.khaled.leanstream.playSoundEffectOnFocus

@Composable
fun CategoryFilterRow(
    channelCategories: List<ChannelCategory>,
    currentSelection: ChannelCategory,
    onSelect: (ChannelCategory) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(channelCategories) {

            var focused by remember { mutableStateOf(false) }

            ElevatedFilterChip(
                modifier = Modifier
                    .playSoundEffectOnFocus()
                    .onFocusEvent { state -> focused = state.isFocused },
                shape = ShapeDefaults.ExtraLarge,
                onClick = { onSelect(it) },
                label = {
                    Text(
                        it.label ?: it.value,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selected = it == currentSelection,
                colors = FilterChipDefaults.elevatedFilterChipColors(
                    selectedContainerColor = LocalContentColor.current,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                ),
                border = if (focused) BorderStroke(2.dp, LocalContentColor.current) else null
            )
        }
    }
}
