package com.skylar.practice.view.draw

import android.animation.PointFEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.skylar.practice.view.Utils
import kotlin.math.sqrt


// 仿喜马拉雅播放状态按钮切换
// Created by skylar on 2022/4/27.
//
class PlayerActionView : View {

    companion object {
        private val BG_COLOR = Color.WHITE
        private val RECT_COLOR = Color.parseColor("#FF65433A")
    }

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

    private var mRightCurrentOne = PointF(0f, 0f)
    private var mRightCurrentTwo = PointF(0f, 0f)
    private var mRightCurrentThree = PointF(0f, 0f)
    private var mRightCurrentFour = PointF(0f, 0f)

    private val mLeftPointFEvaluatorOne = PointFEvaluator(mLeftCurrentOne)
    private val mLeftPointFEvaluatorTwo = PointFEvaluator(mLeftCurrentTwo)
    private val mLeftPointFEvaluatorThree = PointFEvaluator(mLeftCurrentThree)
    private val mLeftPointFEvaluatorFour = PointFEvaluator(mLeftCurrentFour)

    private val mRightPointFEvaluatorOne = PointFEvaluator(mRightCurrentOne)
    private val mRightPointFEvaluatorTwo = PointFEvaluator(mRightCurrentTwo)
    private val mRightPointFEvaluatorThree = PointFEvaluator(mRightCurrentThree)
    private val mRightPointFEvaluatorFour = PointFEvaluator(mRightCurrentFour)

    private var mRectWidth = 0f
    private var mRectHeight = 0f
    private var mFraction = 0f

    private var isPlay = true
    private var valueAnimator : ValueAnimator? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true

        setOnClickListener {
            //播放和暂停切换
            isPlay = !isPlay
            startAnimation()
        }
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

        mRectHeight = Utils.divide(width.toFloat(), 3f)
        mRectWidth = Utils.divide(mRectHeight, 4f)
        val halfHeight = Utils.divide(mRectHeight, 2f)

        mLeftStartOne.set(-mRectWidth * 1.5f, -halfHeight)
        mLeftStartTwo.set(-mRectWidth * 0.5f, -halfHeight)
        mLeftStartThree.set(-mRectWidth * 0.5f, halfHeight)
        mLeftStartFour.set(-mRectWidth * 1.5f, halfHeight)

        //三角形的重心和圆心重合, 三角形才居中
        val halfTriangleHeight = Utils.divide(mRectHeight * sqrt(3f), 6f)
        mLeftEndOne.set(0f, -halfTriangleHeight * 2)
        mLeftEndTwo.set(0f, -halfTriangleHeight * 2)
        mLeftEndThree.set(0f, halfTriangleHeight)
        mLeftEndFour.set(-mRectHeight * 0.5f, halfTriangleHeight)

        mLeftCurrentOne.set(mLeftStartOne.x, mLeftStartOne.y)
        mLeftCurrentTwo.set(mLeftStartTwo.x, mLeftStartTwo.y)
        mLeftCurrentThree.set(mLeftStartThree.x, mLeftStartThree.y)
        mLeftCurrentFour.set(mLeftStartFour.x, mLeftStartFour.y)

        mRightStartOne.set(mRectWidth * 0.5f, -halfHeight)
        mRightStartTwo.set(mRectWidth * 1.5f, -halfHeight)
        mRightStartThree.set(mRectWidth * 1.5f, halfHeight)
        mRightStartFour.set(mRectWidth * 0.5f, halfHeight)

        mRightEndOne.set(0f, -halfTriangleHeight * 2)
        mRightEndTwo.set(0f, -halfTriangleHeight * 2)
        mRightEndThree.set(mRectHeight * 0.5f, halfTriangleHeight)
        mRightEndFour.set(0f, halfTriangleHeight)

        mRightCurrentOne.set(mRightStartOne.x, mRightStartOne.y)
        mRightCurrentTwo.set(mRightStartTwo.x, mRightStartTwo.y)
        mRightCurrentThree.set(mRightStartThree.x, mRightStartThree.y)
        mRightCurrentFour.set(mRightStartFour.x, mRightStartFour.y)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        //画背景圆圈
        mPaint.color = BG_COLOR
        canvas.drawCircle(0f, 0f, (width / 2).toFloat(), mPaint)

        //动画过程中，旋转圆，这样矩形的四个点位置比较好计算，而且两个矩形的变化可以对称
        if(valueAnimator?.isRunning == true || mFraction > 0) {
            if(!isPlay) {
                canvas.rotate(90 * mFraction)
            } else if(isPlay){
                canvas.rotate(90 + 90 * mFraction)
            }
        }

        //画两个path，分别由四个点构成，动画不断改变四个点的位置
        mPaint.color = RECT_COLOR
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
        valueAnimator?.end()

        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator?.duration = 500
        valueAnimator?.addUpdateListener {
            mFraction = it.animatedFraction
            if(!isPlay) {
                computePausePoint()
            } else {
                computePlayPoint()
            }
            invalidate()
        }
        valueAnimator?.start()
    }


    private fun computePausePoint() {
        mLeftPointFEvaluatorOne.evaluate(mFraction, mLeftStartOne, mLeftEndOne)
        mLeftPointFEvaluatorTwo.evaluate(mFraction, mLeftStartTwo, mLeftEndTwo)
        mLeftPointFEvaluatorThree.evaluate(mFraction, mLeftStartThree, mLeftEndThree)
        mLeftPointFEvaluatorFour.evaluate(mFraction, mLeftStartFour, mLeftEndFour)

        mRightPointFEvaluatorOne.evaluate(mFraction, mRightStartOne, mRightEndOne)
        mRightPointFEvaluatorTwo.evaluate(mFraction, mRightStartTwo, mRightEndTwo)
        mRightPointFEvaluatorThree.evaluate(mFraction, mRightStartThree, mRightEndThree)
        mRightPointFEvaluatorFour.evaluate(mFraction, mRightStartFour, mRightEndFour)
    }

    private fun computePlayPoint() {
        mLeftPointFEvaluatorOne.evaluate(mFraction, mLeftEndOne, mLeftStartOne)
        mLeftPointFEvaluatorTwo.evaluate(mFraction, mLeftEndTwo, mLeftStartTwo)
        mLeftPointFEvaluatorThree.evaluate(mFraction, mLeftEndThree, mLeftStartThree)
        mLeftPointFEvaluatorFour.evaluate(mFraction, mLeftEndFour, mLeftStartFour)

        mRightPointFEvaluatorOne.evaluate(mFraction, mRightEndOne, mRightStartOne)
        mRightPointFEvaluatorTwo.evaluate(mFraction, mRightEndTwo, mRightStartTwo)
        mRightPointFEvaluatorThree.evaluate(mFraction, mRightEndThree, mRightStartThree)
        mRightPointFEvaluatorFour.evaluate(mFraction, mRightEndFour, mRightStartFour)
    }
}