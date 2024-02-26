package dev.khaled.leanstream

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.khaled.leanstream.channels.ChannelPicker
import dev.khaled.leanstream.player.Player

@Composable
fun Navigator(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ChannelPicker.name,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(route = Routes.ChannelPicker.name) {
            ChannelPicker {
                navController.navigateSingleTop(it)
            }
        }

        composable(route = Routes.Player.name) {
            Player {
                navController.navigateSingleTop(it)
            }

        }
    }
}

fun NavHostController.navigateSingleTop(route: String) = this.navigate(route) {
    popUpTo(this@navigateSingleTop.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

enum class Routes {
    ChannelPicker, Player
}