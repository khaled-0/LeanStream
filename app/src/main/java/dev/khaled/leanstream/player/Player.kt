package dev.khaled.leanstream.player

import androidx.compose.runtime.Composable
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Player(navigateTo: (route: String) -> Unit) {
    Text("Player")
}