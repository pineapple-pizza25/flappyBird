package com.example.flappybird

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Bird(private val context: Context, private val screenWidth: Int, private val screenHeight: Int) {
    private val paint = Paint()
    private var birdBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bird)
     val birdWidth = screenWidth / 12
     val birdHeight = screenHeight / 12
     var birdX: Float = screenWidth / 2f
     var birdY: Float = screenHeight / 2f
    private var velocity: Float = 0f
    private val gravity: Float = 1.5f
    private val flapPower: Float = -30f


    init {

        birdBitmap = Bitmap.createScaledBitmap(birdBitmap, birdWidth, birdHeight, false)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(birdBitmap, birdX, birdY, paint)
    }

    fun update() {
        velocity += gravity
        birdY += velocity

        if (birdY + birdBitmap.height > screenHeight) {
            birdY = screenHeight - birdBitmap.height.toFloat()
            velocity = 0f
        }

        if (birdY < 0) {
            birdY = 0f
            velocity = 0f
        }


    }

    fun flap() {
        velocity = flapPower
    }

    fun getRect(): Rect {
        return Rect(birdX.toInt(), birdY.toInt(), (birdX + birdBitmap.width).toInt(), (birdY + birdBitmap.height).toInt())
    }
}