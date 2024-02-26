package dev.khaled.leanstream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.khaled.leanstream.ui.theme.LeanStreamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeanStreamTheme {
                Scaffold {
                    Navigator(it)
                }
            }
        }
    }
}