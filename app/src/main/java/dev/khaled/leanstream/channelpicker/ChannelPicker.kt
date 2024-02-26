package dev.khaled.leanstream.channelpicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlin.random.Random
import kotlin.random.asJavaRandom

@Composable
fun ChannelPicker() {

    val list: List<String> = remember {
        List(100) { generateRandomString() }
    }
    LaunchedEffect(Unit) {
        println("LaunchedEffect: entered main")

    }

    ChannelsGrid(items =list)


}


fun generateRandomString(): String {
    val random = Random.asJavaRandom()
    val length = random.nextInt(10) + 1 // Generate strings between 1 and 10 characters
    val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val sb = StringBuilder()
    for (i in 0 until length) {
        val index = random.nextInt(chars.length)
        sb.append(chars[index])
    }
    return sb.toString()
}
