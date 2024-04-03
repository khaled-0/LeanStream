package dev.khaled.leanstream

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.channels.ChannelPickerScreen
import dev.khaled.leanstream.channels.ChannelViewModel
import dev.khaled.leanstream.player.PlayerScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun Navigator() {
    val navController = rememberNavController()
    val channelViewModel: ChannelViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Route.ChannelPicker.route,
    ) {

        composable(route = Route.ChannelPicker.route) {
            ChannelPickerScreen(channelViewModel) { channel ->
                navController.navigateSingleTop(Route.Player.launch(channel))
            }
        }

        composable(
            route = "${Route.Player.route}/{channel}",
            arguments = listOf(navArgument("channel") { type = NavType.StringType })
        ) {
            val serializedChannel = it.arguments?.getString("channel") ?: return@composable
            val channel = Cbor.decodeFromHexString<Channel>(serializedChannel)
            PlayerScreen(channel = channel, channelViewModel.channels) {
                navController.popBackStack()
            }
        }
    }
}

fun NavHostController.navigateSingleTop(route: String) = this.navigate(route) {
    popUpTo(this@navigateSingleTop.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

sealed class Route(val route: String) {
    data object ChannelPicker : Route("ChannelPicker")
    data object Player : Route("Player") {
        @ExperimentalSerializationApi
        fun launch(channel: Channel): String {
            val serializedChannel = Cbor.encodeToHexString(channel)
            return "$route/$serializedChannel"
        }
    }
}