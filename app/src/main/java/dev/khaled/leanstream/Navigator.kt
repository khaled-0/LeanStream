package dev.khaled.leanstream

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.khaled.leanstream.channels.Channel
import dev.khaled.leanstream.channels.ChannelPicker
import dev.khaled.leanstream.player.Player
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString


@OptIn(ExperimentalSerializationApi::class)
@Composable
fun Navigator(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.ChannelPicker.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(route = Route.ChannelPicker.route) {
            ChannelPicker { channel ->
                navController.navigateSingleTop(Route.Player.launch(channel))
            }
        }

        composable(route = "${Route.Player.route}/{channel}", arguments = listOf(
            navArgument("channel") { type = NavType.StringType }
        )) {
            val serializedChannel = it.arguments?.getString("channel") ?: return@composable
            val channel = Cbor.decodeFromHexString<Channel>(serializedChannel)
            Player(channel = channel) {
                navController.navigateSingleTop(Route.ChannelPicker.route)
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