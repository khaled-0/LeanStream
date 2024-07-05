package dev.khaled.leanstream.channels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import net.bjoernpetersen.m3u.M3uParser
import net.bjoernpetersen.m3u.model.M3uEntry
import java.io.File
import java.net.URL

@Serializable
data class Channel(val title: String?, val url: String, val icon: String?, val category: String?)
data class ChannelCategory(val label: String?, val value: String) {
    companion object {
        val All: ChannelCategory = ChannelCategory("All", "")
    }
}

class ChannelViewModel(application: Application) : AndroidViewModel(application) {

    private val _channels = mutableStateListOf<Channel>()
    val channels
        get(): List<Channel> = _channels.filter { matchesFilter(it) }

    val isEmpty get() = _channels.isEmpty()

    private val _categories = mutableStateListOf<ChannelCategory>()
    val categories get(): List<ChannelCategory> = _categories

    private var _isLoading by mutableStateOf(true)
    val isLoading get() = _isLoading

    private var _categoryFilter by mutableStateOf(ChannelCategory.All)
    val categoryFilter get() = _categoryFilter

    private var _searchFilter by mutableStateOf("")
    val searchFilter get() = _searchFilter

    fun applyFilter(category: ChannelCategory = _categoryFilter, search: String = _searchFilter) {
        _categoryFilter = category
        _searchFilter = search
    }


    private fun matchesFilter(channel: Channel): Boolean {
        if (searchFilter.isNotEmpty() && !channel.toString().lowercase()
                .contains(searchFilter.lowercase())
        ) return false

        return _categoryFilter.value.isEmpty() || channel.category.equals(_categoryFilter.value)
    }

    private fun channelFile(context: Context) = File(context.filesDir.absolutePath, "channel.bin")

    init {
        viewModelScope.launch { load(application.applicationContext) }
    }

    fun load(context: Context) {
        _isLoading = true

        runCatching { loadPlaylistFromDisk(context) }

        _categories.addAll(_channels.filter { it.category != null }
            .groupBy { it.category!! }.keys.map { categories ->
                categories.split(";").map { ChannelCategory(it, it) }
            }.flatten().distinctBy { it.value })

        if (_categories.isNotEmpty()) _categories.add(0, ChannelCategory.All)

        _isLoading = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun savePlaylistToDisk(context: Context, channels: List<Channel> = _channels) {
        if (!channelFile(context).exists()) channelFile(context).createNewFile()
        val outputStream = context.openFileOutput(
            channelFile(context).name, Context.MODE_PRIVATE
        )
        outputStream.write(Cbor.encodeToByteArray(channels))
        outputStream.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun loadPlaylistFromDisk(context: Context) {
        if (!channelFile(context).exists()) channelFile(context).createNewFile()
        val fileInputStream = context.openFileInput(channelFile(context).name)
        val bytes = fileInputStream.readBytes()
        fileInputStream.close()

        if (bytes.isEmpty()) return
        _channels.addAll(Cbor.decodeFromByteArray<List<Channel>>(bytes))
    }

    fun parsePlaylist(playlistUrl: String): List<Channel> {
        val input = URL(playlistUrl).openStream().reader()
        val streamEntries: List<M3uEntry> = M3uParser.parse(input)

        if (streamEntries.isEmpty()) throw Exception("Playlist does not contain any entry")

        return streamEntries.map {
            Channel(
                it.title,
                it.location.url.toExternalForm(),
                it.metadata.logo,
                it.metadata["group-title"]
            )
        }
    }
}