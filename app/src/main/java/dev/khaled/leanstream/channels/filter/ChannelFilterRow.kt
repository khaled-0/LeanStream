package dev.khaled.leanstream.channels.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import dev.khaled.leanstream.channels.ChannelCategory
import dev.khaled.leanstream.isRunningOnTV
import dev.khaled.leanstream.playSoundEffectOnFocus

@Composable
fun CategoryFilterRow(
    channelCategories: List<ChannelCategory>,
    currentSelection: ChannelCategory,
    onSelect: (ChannelCategory) -> Unit,
) {
    val context = LocalContext.current
    val isTouchScreen = remember { !isRunningOnTV(context) }

    TvLazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(channelCategories) {
            if (isTouchScreen) {
                FilterChip(onClick = { onSelect(it) },
                    label = { Text(it.label ?: it.value) },
                    selected = it == currentSelection,
                    modifier = Modifier.playSoundEffectOnFocus()
                )
            } else {
                if (it == currentSelection) Button(
                    modifier = Modifier.playSoundEffectOnFocus(),
                    onClick = {}) {
                    Text(it.label ?: it.value, color = MaterialTheme.colorScheme.onBackground)
                } else TextButton(
                    onClick = { onSelect(it) }, modifier = Modifier.playSoundEffectOnFocus()
                ) { Text(it.label ?: it.value) }
            }
        }
    }
}
