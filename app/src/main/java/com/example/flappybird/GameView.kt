package com.example.flappybird

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.random.Random

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs)  {
    private val towers = mutableListOf<Tower>()
    val screenWidth = context.resources.displayMetrics.widthPixels
    val screenHeigh = context.resources.displayMetrics.heightPixels
    val bird = Bird(context, screenWidth, screenHeigh)
    private var isRunning: Boolean = false
    private var towerSpacing = 600f
    private var lastTowerX = 0f
    private var userTapped = false
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 30
    private val towerGenerationDelay: Long = 2000

    init {
        val towerSpacing = 600f
        for (i in 0 until 2) {
            val height = Random.nextInt(50, context.resources.displayMetrics.heightPixels - 200)
            towers.add(Tower(context, i * (towerSpacing + 200), height))

        }
    }

    fun startGameLoop() {
        isRunning = true
        handler.post(object : Runnable {
            override fun run() {
                if (isRunning) {
                    update()
                    invalidate()
                    handler.postDelayed(this, updateInterval)
                }
            }
        })
       // scheduleTowerGeneration()
    }

    fun stopGameLoop() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }

    /*
    draws the towers
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (canvas != null) {
            for (tower in towers) {
                tower.draw(canvas)
            }
            bird.draw(canvas)
        }
        invalidate()
    }


    /*
    updates the position of the bird and towers as necessary
     */
    fun update() {
        if (userTapped) {
            bird.flap()
            userTapped = false
        }
        bird.update()

        for (tower in towers) {
            tower.update()
            if (tower.horiPos + tower.width < 0) {
                tower.horiPos = getNextTowerPosition()
                tower.height = generateValidHeight()
            }
        }

        if (checkCollision()){
            gameOver()
        }
    }

    private fun isPositionValid(newX: Float, newHeight: Int): Boolean {

        val minDistance = 500

        for (tower in towers) {
            val distance = Math.abs(newX - tower.horiPos)

            if (distance < minDistance) {
                return false
            }
        }
        return true
    }

    private fun drawGame(canvas: Canvas) {
        canvas.drawRGB(0, 0, 0)
        bird.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            userTapped = true
            return true
        }
        return super.onTouchEvent(event)
    }

    /*
    checks if the bird collides with any tower
     */
    fun checkCollision(): Boolean {
        val birdRect = Rect(
            bird.birdX.toInt(),
            bird.birdY.toInt(),
            (bird.birdX + bird.birdWidth).toInt(),
            (bird.birdY + bird.birdHeight).toInt()
        )

        for (tower in towers) {
            val topRect = tower.getTopRectangle()
            val bottomRect = tower.getBottomRectangle()

            if (Rect.intersects(birdRect, topRect) || Rect.intersects(birdRect, bottomRect)) {
                return true
            }
        }

        return false
    }

    /*
    game over logic
     */
    private fun gameOver() {
        stopGameLoop()

        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, MenuActivity::class.java)
        context.startActivity(intent)
    }

    /*
    Calculates the position of the next tower
     */
    private fun getNextTowerPosition(): Float {
        val minDistance = 600f
        return if (towers.isEmpty()) {
            width.toFloat()
        } else {
            towers.last().horiPos + 200 + minDistance
        }
    }

    private fun generateValidHeight(): Int {

        val minHeight = 100
        val maxHeight = context.resources.displayMetrics.heightPixels - 800 - minHeight
        return Random.nextInt(minHeight, maxHeight)
    }
}