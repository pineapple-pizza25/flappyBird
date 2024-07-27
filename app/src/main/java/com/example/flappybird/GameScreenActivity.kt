package com.example.flappybird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

class GameScreenActivity : AppCompatActivity() {
    private  lateinit var GameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen2)

        GameView = findViewById(R.id.GameView)

        GameView.startGameLoop()

    }





}

