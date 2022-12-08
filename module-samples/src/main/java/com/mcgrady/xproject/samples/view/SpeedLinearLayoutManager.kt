package com.mcgrady.xproject.samples.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by mcgrady on 2022/12/6.
 */
class SpeedLinearLayoutManager(context: Context, var scrollTime: Int, orientation: Int) :
    LinearLayoutManager(context, orientation, false) {

    private val linearSmoothScroller: LinearSmoothScroller

    init {
        linearSmoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateTimeForDeceleration(dx: Int): Int {
                return scrollTime
            }
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    companion object {

        fun invoke(pager: ViewPager2, intervalTimeMillis: Int) {
            try {
                (pager.getChildAt(0) as RecyclerView?)?.let { recyclerView ->
                    recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                    val orientation =
                        (recyclerView.layoutManager as LinearLayoutManager?)?.orientation
                            ?: ViewPager2.ORIENTATION_HORIZONTAL

                    val layoutManager =
                        SpeedLinearLayoutManager(pager.context, intervalTimeMillis, orientation)

                    recyclerView.layoutManager = layoutManager
//                    recyclerView.isNestedScrollingEnabled = false

                    val layoutMangerField =
                        ViewPager2::class.java.getDeclaredField("mLayoutManager")
                    layoutMangerField.isAccessible = true
                    layoutMangerField.set(pager, layoutManager)

                    val pageTransformerAdapterField =
                        ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
                    pageTransformerAdapterField.isAccessible = true
                    pageTransformerAdapterField.get(pager)?.let { adapter ->
                        val aClass: Class<*> = adapter.javaClass
                        val layoutManagerField = aClass.getDeclaredField("mLayoutManager")
                        layoutManagerField.isAccessible = true
                        layoutManagerField.set(adapter, layoutManager)
                    }

                    val scrollEventAdapterField =
                        ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
                    scrollEventAdapterField.isAccessible = true
                    scrollEventAdapterField.get(pager)?.let { adapter ->
                        val aClass: Class<*> = adapter.javaClass
                        val layoutManagerField = aClass.getDeclaredField("mLayoutManager")
                        layoutManagerField.isAccessible = true
                        layoutManagerField.set(adapter, layoutManager)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}