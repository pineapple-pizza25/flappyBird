package com.example.flappybird

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class Tower(private val context: Context, var horiPos: Float, var height: Int) {
    private val paint = Paint()
    val width: Int = 200
    val gap: Int = 800
    var passed = false


    /*
    draws  top and bottom rectangles
     */
    fun draw(canvas: Canvas){
        val topRect = Rect(horiPos.toInt(), 0, (horiPos + width).toInt(), height)
        val bottomRect = Rect(horiPos.toInt(), height + gap, (horiPos + width).toInt(), canvas.height)
        canvas.drawRect(topRect, paint)
        canvas.drawRect(bottomRect, paint)
    }


    /*
    Moves rectangles to the left
     */
    fun update(){
        horiPos-= 10f
        if (horiPos < width){
            horiPos =context.resources.displayMetrics.widthPixels.toFloat()
            height = generateValidHeight()

        }
    }


    fun getTopRectangle():Rect{
        return Rect(horiPos.toInt(), 0, (horiPos + width).toInt(), height)
    }

    fun getBottomRectangle():Rect{
        return Rect(horiPos.toInt(), height + gap, (horiPos + width).toInt(), context.resources.displayMetrics.heightPixels)
    }

    private fun generateValidHeight(): Int {

        val minHeight = 100
        val maxHeight = context.resources.displayMetrics.heightPixels - gap - minHeight
        return Random.nextInt(minHeight, maxHeight)
    }
}