package com.skylar.practice.view.draw

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.skylar.practice.view.Utils
import com.skylar.practice.view.draw.BezierUtil.CalculateBezierPointForQuadratic


//
// Created by skylar on 2022/4/27.
//
class PlayerActionView : View {

    private val mPaint = Paint()
    private val mLeftPath = Path()
    private val mRightPath = Path()

    private var mLeftStartOne = PointF(0f, 0f)
    private var mLeftStartTwo = PointF(0f, 0f)
    private var mLeftStartThree = PointF(0f, 0f)
    private var mLeftStartFour = PointF(0f, 0f)

    private var mLeftEndOne = PointF(0f, 0f)
    private var mLeftEndTwo = PointF(0f, 0f)
    private var mLeftEndThree = PointF(0f, 0f)
    private var mLeftEndFour = PointF(0f, 0f)

    private var mLeftControlOne = PointF(0f, 0f)
    private var mLeftControlTwo = PointF(0f, 0f)
    private var mLeftControlThree = PointF(0f, 0f)
    private var mLeftControlFour = PointF(0f, 0f)

    private var mLeftCurrentOne = PointF(0f, 0f)
    private var mLeftCurrentTwo = PointF(0f, 0f)
    private var mLeftCurrentThree = PointF(0f, 0f)
    private var mLeftCurrentFour = PointF(0f, 0f)

    private var mRightStartOne = PointF(0f, 0f)
    private var mRightStartTwo = PointF(0f, 0f)
    private var mRightStartThree = PointF(0f, 0f)
    private var mRightStartFour = PointF(0f, 0f)

    private var mRightEndOne = PointF(0f, 0f)
    private var mRightEndTwo = PointF(0f, 0f)
    private var mRightEndThree = PointF(0f, 0f)
    private var mRightEndFour = PointF(0f, 0f)

    private var mRightControlOne = PointF(0f, 0f)
    private var mRightControlTwo = PointF(0f, 0f)
    private var mRightControlThree = PointF(0f, 0f)
    private var mRightControlFour = PointF(0f, 0f)

    private var mRightCurrentOne = PointF(0f, 0f)
    private var mRightCurrentTwo = PointF(0f, 0f)
    private var mRightCurrentThree = PointF(0f, 0f)
    private var mRightCurrentFour = PointF(0f, 0f)

    private var mRectWidth = 0f
    private var mRectHeight = 0f

    private var mFraction = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        val size = Math.min(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRectHeight = Utils.divide(width.toFloat(), 2f)
        mRectWidth = Utils.divide(mRectHeight, 3f)

        mLeftStartOne.set(-mRectWidth * 1.5f, -mRectHeight / 2)
        mLeftStartTwo.set(-mRectWidth * 0.5f, -mRectHeight / 2)
        mLeftStartThree.set(-mRectWidth * 0.5f, mRectHeight / 2)
        mLeftStartFour.set(-mRectWidth * 1.5f, mRectHeight / 2)

        mLeftEndOne.set(mRectWidth * 1.5f, 0f)
        mLeftEndTwo.set(mRectWidth * 1.5f, 0f)
        mLeftEndThree.set(-mRectWidth * 1.5f, 0f)
        mLeftEndFour.set(-mRectWidth * 1.5f, -mRectHeight / 2)

        mLeftCurrentOne.set(mLeftStartOne.x, mLeftStartOne.y)
        mLeftCurrentTwo.set(mLeftStartTwo.x, mLeftStartTwo.y)
        mLeftCurrentThree.set(mLeftStartThree.x, mLeftStartThree.y)
        mLeftCurrentFour.set(mLeftStartFour.x, mLeftStartFour.y)

        mLeftControlOne.set(0f, - (height / 2).toFloat())
        mLeftControlTwo.set(0f, -(height / 2).toFloat())
        mLeftControlThree.set(mLeftStartThree.x, mLeftStartThree.y)
        mLeftControlFour.set((-width / 2).toFloat(),  0f)



        mRightStartOne.set(mRectWidth * 0.5f, -mRectHeight / 2)
        mRightStartTwo.set(mRectWidth * 1.5f, -mRectHeight / 2)
        mRightStartThree.set(mRectWidth * 1.5f, mRectHeight / 2)
        mRightStartFour.set(mRectWidth * 0.5f, mRectHeight / 2)

        mRightEndOne.set(mRectWidth * 1.5f, 0f)
        mRightEndTwo.set(mRectWidth * 1.5f, 0f)
        mRightEndThree.set(-mRectWidth * 1.5f, mRectHeight / 2)
        mRightEndFour.set(-mRectWidth * 1.5f, 0f)

        mRightCurrentOne.set(mRightStartOne.x, mRightStartOne.y)
        mRightCurrentTwo.set(mRightStartTwo.x, mRightStartTwo.y)
        mRightCurrentThree.set(mRightStartThree.x, mRightStartThree.y)
        mRightCurrentFour.set(mRightStartFour.x, mRightStartFour.y)

        mRightControlOne.set(mRightStartOne.x, mRightStartOne.y)
        mRightControlTwo.set(mRightStartTwo.x,mRightStartTwo.y)
        mRightControlThree.set(0f, (height / 2).toFloat())
        mRightControlFour.set(-mRectWidth*1.5f, mRectHeight / 2)

        startAnimation()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        mLeftPath.reset()
        mLeftPath.moveTo(mLeftCurrentOne.x, mLeftCurrentOne.y)
        mLeftPath.lineTo(mLeftCurrentTwo.x, mLeftCurrentTwo.y)
        mLeftPath.lineTo(mLeftCurrentThree.x, mLeftCurrentThree.y)
        mLeftPath.lineTo(mLeftCurrentFour.x, mLeftCurrentFour.y)
        mLeftPath.close()
        canvas.drawPath(mLeftPath, mPaint)

        mRightPath.reset()
        mRightPath.moveTo(mRightCurrentOne.x, mRightCurrentOne.y)
        mRightPath.lineTo(mRightCurrentTwo.x, mRightCurrentTwo.y)
        mRightPath.lineTo(mRightCurrentThree.x, mRightCurrentThree.y)
        mRightPath.lineTo(mRightCurrentFour.x, mRightCurrentFour.y)
        mRightPath.close()
        canvas.drawPath(mRightPath, mPaint)

    }

    private fun startAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 2000
        valueAnimator.repeatCount= ValueAnimator.INFINITE
        valueAnimator.addUpdateListener {
            mFraction = it.animatedFraction
            computePoint()
            invalidate()
        }
        valueAnimator.start()
    }

    private fun computePoint() {
        mLeftCurrentOne = CalculateBezierPointForQuadratic(
            mFraction,
            mLeftStartOne, mLeftControlOne, mLeftEndOne
        )
        mLeftCurrentTwo = CalculateBezierPointForQuadratic(
            mFraction,
            mLeftStartTwo, mLeftControlTwo, mLeftEndTwo
        )
        mLeftCurrentThree = CalculateBezierPointForQuadratic(
            mFraction,
            mLeftStartThree, mLeftControlThree, mLeftEndThree
        )
        mLeftCurrentFour = CalculateBezierPointForQuadratic(
            mFraction,
            mLeftStartFour, mLeftControlFour, mLeftEndFour
        )

        mRightCurrentOne = CalculateBezierPointForQuadratic(
            mFraction,
            mRightStartOne, mRightControlOne, mRightEndOne
        )
        mRightCurrentTwo = CalculateBezierPointForQuadratic(
            mFraction,
            mRightStartTwo, mRightControlTwo, mRightEndTwo
        )
        mRightCurrentThree = CalculateBezierPointForQuadratic(
            mFraction,
            mRightStartThree, mRightControlThree, mRightEndThree
        )
        mRightCurrentFour = CalculateBezierPointForQuadratic(
            mFraction,
            mRightStartFour, mRightControlFour, mRightEndFour
        )
    }

    private fun printPoint() {
        Log.i("PlayerActionView", "one : ${mLeftStartOne.x}, ${mLeftStartOne.y}")
        Log.i("PlayerActionView", "two : ${mLeftStartTwo.x}, ${mLeftStartTwo.y}")
        Log.i("PlayerActionView", "three : ${mLeftStartThree.x}, ${mLeftStartThree.y}")
        Log.i("PlayerActionView", "four : ${mLeftStartFour.x}, ${mLeftStartFour.y}")
    }
}


//class BezierEvaluator(private val mControlPoint: PointF) :
//    TypeEvaluator<PointF> {
//    override fun evaluate(t: Float, startValue: PointF, endValue: PointF): PointF {
//        return BezierUtil.CalculateBezierPointForQuadratic(t, startValue, mControlPoint, endValue)
//    }
//}