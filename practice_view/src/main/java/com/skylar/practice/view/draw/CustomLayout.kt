package com.skylar.practice.view.draw

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import com.skylar.practice.view.R
import com.skylar.practice.view.Utils.divide

//
// Created by skylar on 2022/5/8.
//
class CustomLayout : ViewGroup {
    private val TAG = "CustomLayout"

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    val text = TextView(context).apply {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        textSize = 18f
        setTextColor(Color.BLACK)
        setText("hello")
        addView(this)
    }

    val icon = ImageView(context).apply {
        setImageResource(R.drawable.avatar)
        layoutParams = LayoutParams(50.dp, 50.dp)
        addView(this)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        text.autoMeasure()
        icon.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        Log.i(TAG, "onLayout text : ${measuredWidth} /  ${text.measuredWidth} / ${icon.measuredWidth}")

        val startX = (measuredWidth - text.measuredWidth - icon.measuredWidth).divide(2)
        val startTextY = (measuredHeight - text.measuredHeight).divide(2)
        val startIconY = (measuredHeight - icon.measuredHeight).divide(2)

        text.autoLayout(startX, startTextY)
        icon.autoLayout(startX + text.width, startIconY)
    }

    private fun View.autoLayout(x: Int, y: Int) {
        layout(x,y, x + measuredWidth, y + measuredHeight)
    }

    private fun View.autoMeasure() {
        measure(measureWidth(this@CustomLayout), measureHeight(this@CustomLayout))
    }

    private fun View.measureWidth(parentView: ViewGroup) : Int {
        return when(layoutParams.width) {
            MATCH_PARENT ->  MeasureSpec.makeMeasureSpec(parentView.measuredWidth, MeasureSpec.AT_MOST)
            WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(parentView.measuredWidth, MeasureSpec.AT_MOST)
            0 -> MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            else -> MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY)
        }
    }

    private fun View.measureHeight(parentView: ViewGroup) : Int {
        return when(layoutParams.height) {
            MATCH_PARENT ->  MeasureSpec.makeMeasureSpec(parentView.measuredHeight, MeasureSpec.AT_MOST)
            WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(parentView.measuredHeight, MeasureSpec.AT_MOST)
            0 -> MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            else -> MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY)
        }
    }

    val Int.dp: Int get() = (this * resources.displayMetrics.density + 0.5f).toInt()


    class LayoutParams(width: Int, height: Int) : MarginLayoutParams(width, height)

}