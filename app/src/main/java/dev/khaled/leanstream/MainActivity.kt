package dev.khaled.leanstream

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
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

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier =
    if (condition) then(modifier(Modifier)) else this

//TODO
@Composable
fun Modifier.playSoundEffectOnFocus(
    effectType: Int = AudioManager.FX_FOCUS_NAVIGATION_UP
): Modifier {
    val context = LocalContext.current
    val audioManager = remember {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    return this
        .onFocusChanged {
            if (it.isFocused) {
                audioManager.playSoundEffect(effectType)
            }
        }
}