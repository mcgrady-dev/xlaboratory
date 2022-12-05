package com.mcgrady.xproject.samples.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mcgrady.xproject.samples.view.CustomViewActivity.Companion.getAction
import kotlin.math.roundToInt

/**
 * Created by mcgrady on 2021/12/25.
 */
class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var lastY: Float? = 0F
    private var lastX: Float? = 0F

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        when (widthSpecMode) {
            MeasureSpec.UNSPECIFIED -> {
                Log.e("CustomView","widthSpecMode=UNSPECIFIED")
            }
            MeasureSpec.EXACTLY -> {
                Log.e("CustomView","widthSpecMode=EXACTLY")
            }
            MeasureSpec.AT_MOST -> {
                Log.e("CustomView","widthSpecMode=AT_MOST")
            }
        }

        when (heightSpecMode) {
            MeasureSpec.UNSPECIFIED -> {
                Log.e("CustomView","heightSpecMode=UNSPECIFIED")
            }
            MeasureSpec.EXACTLY -> {
                Log.e("CustomView","heightSpecMode=EXACTLY")
            }
            MeasureSpec.AT_MOST -> {
                Log.e("CustomView","heightSpecMode=AT_MOST")
            }
        }

        Log.e("CustomView", "widthSpecMode=$widthSpecMode heightSpecMode=$heightSpecMode width=$width height=$height")

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d(CustomViewActivity.INPUT_TAG, "$TAG dispatchTouchEvent event=${getAction(event?.action)} return super")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        //return testActionMove(event)

        Log.d(CustomViewActivity.INPUT_TAG, "$TAG onTouchEvent event=${getAction(event?.action)} return super")
        return super.onTouchEvent(event)
    }

    fun testActionMove(event: MotionEvent?): Boolean {
        val x = event?.x ?: 0F
        val y = event?.y ?: 0F

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = x.minus(lastX ?: 0F).roundToInt()
                val offsetY = y.minus(lastY ?: 0F).roundToInt()

//                layout(
//                    left + offsetX, top + offsetY,
//                    right + offsetX, bottom + offsetY
//                )

//                offsetLeftAndRight(offsetX)
//                offsetTopAndBottom(offsetY)

                var lp = (layoutParams as ConstraintLayout.LayoutParams).apply {
                    leftMargin = left + offsetX
                    topMargin = top + offsetY
                }
                layoutParams = lp

            }
            else -> {}
        }
        return true
    }

    companion object {
        const val TAG = "CustomView"
    }
}
