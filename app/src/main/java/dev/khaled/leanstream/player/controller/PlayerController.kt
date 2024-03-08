package dev.khaled.leanstream.player.controller

import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.exoplayer.ExoPlayer
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.conditional
import dev.khaled.leanstream.isRunningOnTV
import dev.khaled.leanstream.ui.ScreenOrientation

@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    channel: Channel,
    player: ExoPlayer,
    backHandler: () -> Unit,
) {


    val isRunningOnTV = isRunningOnTV(LocalContext.current)

    var controllerVisible by remember { mutableStateOf(true) }

    var isPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }


    val handler = remember { Handler(Looper.getMainLooper()) }
    val controllerVisibilityRunnable =
        remember { Runnable { if (controllerVisible) controllerVisible = false } }

    fun triggerHideController() = run {
        handler.removeCallbacks(controllerVisibilityRunnable)
        handler.postDelayed(controllerVisibilityRunnable, 3000)
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                super.onIsPlayingChanged(playing)
                isPlaying = playing
                if (isPlaying) error = null
                if (error == null) triggerHideController()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                isBuffering = playbackState == STATE_BUFFERING
                controllerVisible = true
            }

            override fun onPlayerError(exception: PlaybackException) {
                super.onPlayerError(exception)
                error = exception.message
                controllerVisible = true
            }
        }

        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
        }
    }


    ScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    Box(modifier = Modifier
        .fillMaxSize()
        .conditional(!controllerVisible) {
            clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    controllerVisible = true
                    triggerHideController()
                })
        })



    AnimatedVisibility(
        modifier = modifier.fillMaxSize(), visible = controllerVisible,
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .conditional(!isRunningOnTV) {
                clickable { controllerVisible = false }
            }
            .background(Color.Black.copy(alpha = 0.3f))) {

            Box(modifier = Modifier.align(Alignment.Center)) {
                if (isBuffering) CircularProgressIndicator()
                else PlayPauseToggle(isPlaying = isPlaying, error = error) {
                    if (error != null) {
                        error = null
                        return@PlayPauseToggle player.prepare()
                    }

                    if (isPlaying) player.pause()
                    else player.play()
                    triggerHideController()
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                if (!isRunningOnTV) ExtraControls(backHandler)

                Spacer(modifier = Modifier.weight(1f))

                ChannelInfo(channel = channel)
            }
        }
    }
}


