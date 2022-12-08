package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by mcgrady on 2022/12/8.
 */
class ChildRecyclerView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FlingRecyclerView(context, attrs, defStyle) {


//    fun findViewByPositionFromViewPager(): View? {
//        if (parent is ViewPager2) {
//            parent.view
//        }
//
//        return null
//    }
}