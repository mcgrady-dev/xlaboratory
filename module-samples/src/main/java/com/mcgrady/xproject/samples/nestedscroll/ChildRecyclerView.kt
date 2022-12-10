package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mcgrady.xproject.samples.R
import kotlin.math.abs

/**
 * Created by mcgrady on 2022/12/8.
 */
class ChildRecyclerView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FlingRecyclerView(context, attrs, defStyle) {

    private var startX: Float = 0F
    private var startY: Float = 0F
    private var startRawX: Float = 0F
    private var startRawY: Float = 0F
    private var touchSlop: Int
    private var dragState: Int = DRAG_IDLE

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

//        val canonicalName = parent::class.java.canonicalName
//        if (canonicalName == "androidx.viewpager2.widget.ViewPager2.RecyclerViewImpl") {
//
//        }
    }

    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        val x = e.rawX
        val y = e.rawY
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = e.x
                val endY = e.y
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)

                if (distanceY > touchSlop && distanceY > distanceX) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP or MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(e)
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                startRawX = e.rawX
                startRawY = e.rawY
                stopFling()
            }
            MotionEvent.ACTION_MOVE -> {
                val endRawX = e.x
                val endRawY = e.y
                val distanceX = abs(endRawX - startRawX)
                val distanceY = abs(endRawY - startRawY)
                if (distanceY > distanceX && distanceY > touchSlop) {
                    return true
                } else if (distanceX > distanceY && distanceX > touchSlop) {
                    return true
                }
            }
            else -> {}
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endRawX = e.x
                val endRawY = e.y
                val distanceX = abs(endRawX - startRawX)
                val distanceY = abs(endRawY - startRawY)
                if (distanceY > distanceX && distanceY > touchSlop) {
                    dragState = DRAG_VERTICAL
                    parent.requestDisallowInterceptTouchEvent(true)
                } else if (distanceX > distanceY && distanceX > touchSlop) {
                     dragState = DRAG_HORIZONTAL
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            else -> {}
        }
        return super.onTouchEvent(e)
    }

    companion object {

        private const val DRAG_IDLE = 0
        private const val DRAG_VERTICAL = 1
        private const val DRAG_HORIZONTAL = 2

        fun reflect(viewPager: ViewPager2): ChildRecyclerView? {
            val layoutManagerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
            layoutManagerField.isAccessible = true
            val layoutManager = layoutManagerField.get(viewPager) as LinearLayoutManager
            var currentChild = layoutManager.findViewByPosition(viewPager.currentItem)

            if (currentChild is ChildRecyclerView) {
                return currentChild
            } else {
                val tagView = currentChild?.getTag(R.id.sticky_child_recycler_view)
                if (tagView is ChildRecyclerView) {
                    return tagView
                }
            }

            return null
        }
    }
}