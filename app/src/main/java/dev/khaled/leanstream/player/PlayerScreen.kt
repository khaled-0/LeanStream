package dev.khaled.leanstream.player

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.player.controller.PlayerController
import io.github.anilbeesetti.nextlib.media3ext.ffdecoder.NextRenderersFactory


@Composable
fun PlayerScreen(channel: Channel, playlist: List<Channel>, navigateBack: () -> Unit) {
    val exoPlayer = rememberExoPlayer(LocalContext.current)
    LaunchedEffect(Unit) {
        // FIXME OOM When playlist large af
        playlist.forEach {
            exoPlayer.addMediaItem(
                MediaItem.Builder().setUri(it.url).setMediaMetadata(
                    MediaMetadata.Builder().setDisplayTitle(it.title)
                        .setArtworkUri(it.icon?.let { i -> Uri.parse(i) }).build()
                ).build()
            )
        }

        exoPlayer.seekTo(playlist.indexOf(channel), 0)
        exoPlayer.prepare()
    }

    BackHandler(onBack = navigateBack)

    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        PlayerView(it).apply {
            player = exoPlayer
            useController = false
        }
    }, update = { it.player = exoPlayer }, onRelease = { exoPlayer.release() })


    PlayerController(
        modifier = Modifier.fillMaxSize(), player = exoPlayer, backHandler = navigateBack
    )
}


@OptIn(UnstableApi::class)
@Composable
private fun rememberExoPlayer(context: Context) = remember {
    val renderersFactory = NextRenderersFactory(context).setExtensionRendererMode(
        DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
    )

    ExoPlayer.Builder(context).setRenderersFactory(renderersFactory)
        .build().apply {
            playWhenReady = true
        }
}
