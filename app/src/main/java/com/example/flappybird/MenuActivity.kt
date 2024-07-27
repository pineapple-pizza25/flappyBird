package com.example.flappybird

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    private lateinit var startButtton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        startButtton = findViewById(R.id.start_button)

        startButtton.setOnClickListener(){
            val intent = Intent(this, GameScreenActivity::class.java)
            startActivity(intent)
        }
    }
}