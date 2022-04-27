package com.skylar.practice.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import java.math.BigDecimal

object Utils {

    fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            Resources.getSystem().displayMetrics
        )
    }

    fun getAvatar(resources: Resources, width: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }

    //arg1 除以 arg2 的精确结果， 直接用 / 会丢失准度
    fun divide(arg1: Float, arg2: Float): Float {
        val b1 = BigDecimal(java.lang.Float.toString(arg1))
        val b2 = BigDecimal(java.lang.Float.toString(arg2))
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toFloat()
    }
}