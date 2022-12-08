package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.NestedScrollingParent3
import com.mcgrady.xproject.samples.R

/**
 * Created by mcgrady on 2022/12/8.
 */
class ParentRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FlingRecyclerView(context, attrs, defStyle), NestedScrollingParent3 {


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return target.id == R.id.sticky_child_recycler_view
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
    }

}