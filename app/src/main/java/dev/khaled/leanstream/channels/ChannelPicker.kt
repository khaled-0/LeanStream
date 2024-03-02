package dev.khaled.leanstream.channels

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import dev.khaled.leanstream.channels.importer.ImportPlaylistPrompt
import dev.khaled.leanstream.ui.Branding

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTvMaterial3Api::class)
@Composable
fun ChannelPicker(openChannel: (channel: Channel) -> Unit) {
    val viewModel: ChannelViewModel = viewModel()
    val context = LocalContext.current
    val compactAppBar = true

    if (viewModel.isLoading) {
        return Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
            )
        }
    }

    if (viewModel.channels.isEmpty()) {
        return Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            ImportPlaylistPrompt {
                viewModel.load(context)
            }
        }
    }



    Column {

        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Branding()
                Divider(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(16.dp)
                        .width(1.5.dp)
                )
                if (compactAppBar) {
                    CategoryFilterRow(
                        channelCategories = viewModel.categories,
                        currentSelection = viewModel.categoryFilter
                    ) { viewModel.applyFilter(it) }
                }
            }
        }, actions = {
            IconButton(onClick = {}, content = {
                Icon(Icons.Rounded.Search, null)
            })

            IconButton(onClick = {

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

        ChannelsGrid(items = viewModel.channels) {
            openChannel(it)
        }
    }
}