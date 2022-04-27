package com.skylar.practice.view.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.skylar.practice.view.R
import com.skylar.practice.view.Utils

// 圆形头像
// Created by skylar on 2022/4/27.
//
class CircleView : View {

    private val mPaint: Paint = Paint()
    private var mSize = 0
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private var mBitmap :Bitmap? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL

        mSize = Utils.dpToPx(100f).toInt()
        mBitmap = Utils.getAvatar(resources, mSize)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(mSize, widthMeasureSpec)
        val height = resolveSize(mSize, widthMeasureSpec)
        val size = Math.min(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        val radius = (width / 2).toFloat()
        //开启离屏缓冲， 提起来画
        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        canvas.drawCircle(radius, radius, radius, mPaint)
        mPaint.xfermode = mXfermode
        mBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, mPaint)
        }
        mPaint.xfermode = null
        canvas.restoreToCount(saveLayer)
    }
}