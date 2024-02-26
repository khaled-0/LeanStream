package dev.khaled.leanstream.channels.importer

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ImportPlaylist() {
    var text by remember { mutableStateOf("") }

    TextField(value = text, onValueChange = { text = it }, label = { Text("Enter your text:") })
}