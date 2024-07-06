package dev.khaled.leanstream.preferences

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.khaled.leanstream.channels.ChannelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(viewModel: ChannelViewModel) {
    val context = LocalContext.current

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Preferences") }) }) { contentPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                FilledTonalIconButton(
                    onClick = {
                        resetChannelsListConfirmation(
                            context, viewModel
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Remove All Imported Channels")
                }
            }
        }
    }
}

fun resetChannelsListConfirmation(context: Context, viewModel: ChannelViewModel) {
    AlertDialog.Builder(context).setTitle("Reset Channels")
        .setMessage("Do you really want to remove imported channels?")
        .setPositiveButton("Yes") { _, _ ->
            viewModel.savePlaylistToDisk(context, emptyList())
            Toast.makeText(context, "Restart Application To Take Effect", Toast.LENGTH_SHORT).show()
        }.setNegativeButton("No", null).show()
}