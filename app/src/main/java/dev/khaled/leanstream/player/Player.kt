package dev.khaled.leanstream.player

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.player.controller.PlayerController


@Composable
fun Player(channel: Channel, navigateBack: () -> Unit) {
    val exoPlayer = rememberExoPlayer(LocalContext.current)
    val mediaSource = remember { MediaItem.fromUri(channel.url) }

    LaunchedEffect(Unit) {
        exoPlayer.setMediaItem(mediaSource)
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
        modifier = Modifier.fillMaxSize(),
        channel = channel,
        player = exoPlayer,
        backHandler = navigateBack
    )
}


@Composable
private fun rememberExoPlayer(context: Context) = remember {
    ExoPlayer.Builder(context).build().apply {
        playWhenReady = true
    }
}

//private fun Modifier.dPadEvents(
//    exoPlayer: ExoPlayer,
//    videoPlayerState: VideoPlayerState,
//    pulseState: VideoPlayerPulseState
//): Modifier = this.handleDPadKeyEvents(
//    onLeft = {
//        exoPlayer.seekBack()
//        pulseState.setType(BACK)
//    },
//    onRight = {
//        exoPlayer.seekForward()
//        pulseState.setType(FORWARD)
//    },
//    onUp = { videoPlayerState.showControls() },
//    onDown = { videoPlayerState.showControls() },
//    onEnter = {
//        exoPlayer.pause()
//        videoPlayerState.showControls()
//    }
//)