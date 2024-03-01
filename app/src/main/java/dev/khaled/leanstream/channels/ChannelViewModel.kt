package dev.khaled.leanstream.channels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import net.bjoernpetersen.m3u.M3uParser
import net.bjoernpetersen.m3u.model.M3uEntry
import java.io.File
import java.net.URL
import kotlin.OptIn
import androidx.annotation.OptIn as AndroidxAnnotationOptIn


@Serializable
data class Channel(val title: String?, val url: String, val icon: String?)

class ChannelViewModel : ViewModel() {
    private val _channels = mutableStateListOf<Channel>()
    val channels get(): List<Channel> = _channels

    private var _isLoading by mutableStateOf(true)
    val isLoading get() = _isLoading

    private fun channelFile(context: Context) = File(context.filesDir.absolutePath, "channel.bin")

    @OptIn(ExperimentalSerializationApi::class)
    fun load(context: Context) {
        _isLoading = true
        loadPlaylistFromDisk(context)
        _isLoading = false
    }

    @ExperimentalSerializationApi
    fun savePlaylistToDisk(context: Context, channels: List<Channel> = _channels) {
        if (!channelFile(context).exists()) channelFile(context).createNewFile()
        val outputStream = context.openFileOutput(
            channelFile(context).name, Context.MODE_PRIVATE
        )
        outputStream.write(Cbor.encodeToByteArray(channels))
        outputStream.close()
    }

    @ExperimentalSerializationApi
    fun loadPlaylistFromDisk(context: Context) {
        if (!channelFile(context).exists()) channelFile(context).createNewFile()
        val fileInputStream = context.openFileInput(channelFile(context).name)
        val bytes = fileInputStream.readBytes()
        fileInputStream.close()

        if (bytes.isEmpty()) return
        _channels.addAll(Cbor.decodeFromByteArray<List<Channel>>(bytes))
    }

    @AndroidxAnnotationOptIn(UnstableApi::class)
    fun parsePlaylist(playlistUrl: String): List<Channel> {
        val input = URL(playlistUrl).openStream().reader()
        val streamEntries: List<M3uEntry> = M3uParser.parse(input)

        if (streamEntries.isEmpty()) throw Exception("Playlist does not contain any entry")

        return streamEntries.map {
            Channel(it.title, it.location.url.toExternalForm(), it.metadata.logo)
        }
    }
}