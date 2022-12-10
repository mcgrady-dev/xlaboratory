package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.OverScroller
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field

/**
 * Created by mcgrady on 2022/12/8.
 */
open class FlingRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var overScroller: OverScroller? = null
    private var scrollerY: Any? = null
    private var velocityYField: Field? = null

    val velocityY: Int
        get() {
            return (velocityYField?.get(scrollerY) as Float).toInt()
        }

    init {

        try {
            val viewFlingerField = RecyclerView::class.java.getDeclaredField("mViewFlinger")
            viewFlingerField.isAccessible = true
            val viewFlinger = viewFlingerField.get(this)
            val overScrollerField = viewFlinger.javaClass.getDeclaredField("mOverScroller")
            overScrollerField.isAccessible = true
            overScroller = overScrollerField.get(viewFlinger) as OverScroller

            val scrollerYFiled = OverScroller::class.java.getDeclaredField("mScrollerY")
            scrollerYFiled.isAccessible = true
            scrollerY = scrollerYFiled.get(overScroller)

            velocityYField = scrollerY?.javaClass?.getDeclaredField("mCurrVelocity")
            velocityYField?.isAccessible = true

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun stopFling() {
        overScroller?.forceFinished(true)
        scrollerY?.let {
            velocityYField?.set(it, 0F)
        }
    }
}