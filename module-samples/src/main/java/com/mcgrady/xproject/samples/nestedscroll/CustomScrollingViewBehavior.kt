package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior

/**
 * Created by mcgrady on 2022/12/8.
 */
class CustomScrollingViewBehavior(context: Context?, attrs: AttributeSet?) : ScrollingViewBehavior(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
//        Log.d(TAG, "layoutDependsOn: parent=${parent.javaClass.simpleName}, child=${child.javaClass.simpleName}, dependency=${dependency.javaClass.simpleName}")
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
//        Log.d(TAG, "onDependentViewChanged: parent=${parent.javaClass.simpleName}, child=${child.javaClass.simpleName}, dependency=${dependency.javaClass.simpleName}")
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
//        Log.d(TAG, "onDependentViewRemoved: parent=${parent.javaClass.simpleName}, child=${child.javaClass.simpleName}, dependency=${dependency.javaClass.simpleName}")
        super.onDependentViewRemoved(parent, child, dependency)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        Log.d(TAG, "onStartNestedScroll: child=${child.javaClass.simpleName}, directTargetChild=${directTargetChild.javaClass.simpleName}, target=${target.javaClass.simpleName}, axes=$axes, type=$type")
        return super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        Log.d(TAG, "onNestedScrollAccepted: child=${child.javaClass.simpleName}, directTargetChild=${directTargetChild.javaClass.simpleName}, target=${target.javaClass.simpleName}, axes=$axes, type=$type")
        super.onNestedScrollAccepted(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        Log.d(TAG, "onStopNestedScroll: child=${child.javaClass.simpleName}, target=${target.javaClass.simpleName}, type=$type")
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.d(TAG, "onNestedScroll: child=${child.javaClass.simpleName}, target=${target.javaClass.simpleName}, dxConsumed=$dxConsumed, dyConsumed=$dyConsumed, dxUnconsumed=$dxUnconsumed, dyUnconsumed=$dyUnconsumed, type=$type, consumed=$consumed")
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        Log.d(TAG, "onNestedPreScroll: child=${child.javaClass.simpleName}, target=${target.javaClass.simpleName}, dx=$dx, dy=$dy, consumed=$consumed, type=$type")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.d(TAG, "onNestedFling: child=${child.javaClass.simpleName}, target=${target.javaClass.simpleName}, velocityX=$velocityX, velocityY=$velocityY, consumed=$consumed")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(TAG, "onNestedPreFling: child=${child.javaClass.simpleName}, target=${target.javaClass.simpleName}, velocityX=$velocityX, velocityY=$velocityY")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    companion object {
        const val TAG = "Behavior"
    }
}