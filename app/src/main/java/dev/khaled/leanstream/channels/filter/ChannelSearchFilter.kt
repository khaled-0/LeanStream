package dev.khaled.leanstream.channels.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChannelSearchFilter(
    currentValue: String,
    onEdit: (String) -> Unit,
) {
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        value = currentValue,
        onValueChange = onEdit,
        placeholder = { Text("Search for channel by title, url, category") })
}
