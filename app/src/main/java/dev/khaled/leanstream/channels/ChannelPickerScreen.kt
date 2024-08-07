package dev.khaled.leanstream.channels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.khaled.leanstream.Route
import dev.khaled.leanstream.channels.filter.CategoryFilterRow
import dev.khaled.leanstream.channels.filter.ChannelSearchFilter
import dev.khaled.leanstream.channels.importer.ImportPlaylistPrompt
import dev.khaled.leanstream.channels.item.ChannelsGrid
import dev.khaled.leanstream.isRunningOnTV
import dev.khaled.leanstream.playSoundEffectOnFocus
import dev.khaled.leanstream.ui.Branding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelPickerScreen(
    viewModel: ChannelViewModel = viewModel(),
    openChannel: (channel: Channel) -> Unit,
    navigateTo: (route: Route) -> Unit,
) {
    val context = LocalContext.current
    val compactAppBar = remember { isRunningOnTV(context) }

    if (viewModel.isLoading) {
        return Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
            )
        }
    }

    if (viewModel.isEmpty) {
        return Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            ImportPlaylistPrompt {
                viewModel.load(context)
            }
        }
    }


    var searchToggled by rememberSaveable { mutableStateOf(false) }

    Column {

        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Branding()

                if (compactAppBar) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .height(16.dp)
                            .width(1.5.dp)
                    )

                    CategoryFilterRow(
                        channelCategories = viewModel.categories,
                        currentSelection = viewModel.categoryFilter
                    ) { viewModel.applyFilter(it) }
                }
            }
        }, actions = {
            var focused by remember { mutableStateOf(false) }
            OutlinedIconToggleButton(checked = searchToggled,
                onCheckedChange = { searchToggled = it },
                modifier = Modifier
                    .playSoundEffectOnFocus()
                    .onFocusEvent { state -> focused = state.isFocused },
                border = if (focused) BorderStroke(2.dp, LocalContentColor.current) else null,
                content = {
                    Icon(Icons.Rounded.Search, null)
                })

            IconButton(onClick = {
                navigateTo(Route.Preferences)
            }, content = {
                Icon(Icons.Outlined.Settings, null)
            })
        })

        if (!compactAppBar) {
            CategoryFilterRow(
                channelCategories = viewModel.categories,
                currentSelection = viewModel.categoryFilter
            ) { viewModel.applyFilter(it) }
        }

        if (searchToggled) ChannelSearchFilter(viewModel.searchFilter) {
            viewModel.applyFilter(search = it)
        }

        ChannelsGrid(items = viewModel.channels) {
            openChannel(it)
        }
    }
}