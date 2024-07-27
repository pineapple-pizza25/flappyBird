package com.example.flappybird

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.flappybird.ui.theme.FlappyBirdTheme


class MainActivity : ComponentActivity() {
 private  lateinit var GameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            FlappyBirdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlappyBirdTheme {
        Greeting("Android")
    }
}

@Composable
fun GameScreenCompos() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val gameView = GameView(context)

    AndroidView(factory = { gameView })

    startGameLoop(gameView)
}

fun startGameLoop(gameView: GameView) {

    val thread = Thread {
        while (true) {
            gameView.post {
                gameView.update()
            }
            Thread.sleep(16)
        }
    }
    thread.start()
}