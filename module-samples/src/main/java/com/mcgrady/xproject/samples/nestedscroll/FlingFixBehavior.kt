package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import com.google.android.material.appbar.AppBarLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.lang.reflect.Field
import kotlin.Throws

/**
 * Created by mcgrady on 2022/12/5.
 */
class FlingFixBehavior(context: Context?, attrs: AttributeSet?) : AppBarLayout.Behavior(context, attrs) {

    private var isFlinging = false
    private var shouldBlockNestedScroll = false

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ev=$ev, child=$child, child.totalScrollRange=${child.totalScrollRange}")
        shouldBlockNestedScroll = isFlinging
        when (ev.actionMasked) {
            //手指触摸屏幕的时候停止fling事件
            MotionEvent.ACTION_DOWN -> stopAppbarLayoutFling(child)
            else -> {}
        }

        return super.onInterceptTouchEvent(parent, child, ev)
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    @get:Throws(NoSuchFieldException::class)
    private val flingRunnableField: Field?
        private get() {
            val superclass: Class<*>? = this.javaClass.superclass
            return try {
                // support design 27及一下版本
                var headerBehaviorType: Class<*>? = superclass?.superclass
                headerBehaviorType?.getDeclaredField("mFlingRunnable")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
                // 可能是28及以上版本
                val headerBehaviorType = superclass?.superclass?.superclass
                headerBehaviorType?.getDeclaredField("flingRunnable")
            }
        }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    @get:Throws(NoSuchFieldException::class)
    private val scrollerField: Field?
        private get() {
            val superclass: Class<*>? = this.javaClass.superclass
            return try {
                // support design 27及一下版本
                var headerBehaviorType: Class<*>? = superclass?.superclass
                headerBehaviorType?.getDeclaredField("mScroller")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
                // 可能是28及以上版本
                val headerBehaviorType = superclass?.superclass?.superclass
                headerBehaviorType?.getDeclaredField("scroller")
            }
        }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private fun stopAppbarLayoutFling(appBarLayout: AppBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            val flingRunnableField = flingRunnableField
            val scrollerField = scrollerField

            flingRunnableField?.isAccessible = true
            scrollerField?.isAccessible = true

            val flingRunnable = flingRunnableField?.get(this) as Runnable?
            flingRunnable?.let {
                Log.d(TAG, "存在flingRunnable")
                appBarLayout.removeCallbacks(it)
                flingRunnableField?.set(this, null)
            }

            val overScroller = scrollerField?.get(this) as OverScroller?
            overScroller?.let {
                if (!it.isFinished) {
                    it.abortAnimation()
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout, child: AppBarLayout,
        directTargetChild: View, target: View,
        nestedScrollAxes: Int, type: Int
    ): Boolean {
        Log.d(TAG, "onStartNestedScroll")
        stopAppbarLayoutFling(child)
        return super.onStartNestedScroll(
            parent, child, directTargetChild, target,
            nestedScrollAxes, type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout, target: View,
        dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        Log.d(TAG, "onNestedPreScroll: ${child.totalScrollRange}  ,dx:$dx ,dy:$dy , type:$type")
        //type返回1时，表示当前target处于非touch的滑动，
        //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动
        //子类还未结束其自身的fling
        //所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.d(TAG, "onNestedScroll: target: ${target.javaClass} ,${child.totalScrollRange} ,dxConsumed: $dxConsumed ,dyConsumed:$dyConsumed ,type:$type")
        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(
                coordinatorLayout, child, target, dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, type
            )
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout, abl: AppBarLayout,
        target: View, type: Int
    ) {
        Log.d(TAG, "onStopNestedScroll")
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
        isFlinging = false
        shouldBlockNestedScroll = false
    }


    companion object {
        private const val TAG = "FlingFixBehavior"
        private const val TYPE_FLING = 1
    }
}