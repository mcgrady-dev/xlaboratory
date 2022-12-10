package com.mcgrady.xproject.samples.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import com.mcgrady.xproject.samples.R

/**
 * Created by mcgrady on 2022/12/8.
 */
class ParentRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FlingRecyclerView(context, attrs, defStyle), NestedScrollingParent3 {

    private val childViewHelper: ChildViewHelper = ChildViewHelper()

    var stickyHeight: Int = 0
        set(value) {
            if (field == value) {
                return
            }

            val offset = field - value
            field = value
            requestChildViewLayout()

            post {
                scrollBy(0, offset)
            }
        }

    var innerStickyTop: Boolean = false
    var onStickyListener: ((isTop: Boolean) -> Unit?)? = null

    init {

    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                findChildViewUnder(e.x, e.y)?.let { child ->
                    getChildViewHolder(child)?.let { viewHolder ->
                        childViewHelper.container = viewHolder.itemView
                        val childRecyclerView = childViewHelper.callback?.findCurrentChildRecyclerView(viewHolder)
                        childViewHelper.child = childRecyclerView

                        val intercept =
                            onInterceptChildTouch(e, viewHolder.itemView, childRecyclerView)

                        this.stopFling()
                        childRecyclerView?.stopFling()

                        return if (intercept) false else super.onInterceptTouchEvent(e)
                    }
                }
            }
            else -> {}
        }
        return super.onInterceptTouchEvent(e)
    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return target is ChildRecyclerView
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
        if (target is ChildRecyclerView) {
            val childScrollY = target.computeVerticalScrollOffset()
            val containerTop = childViewHelper.container?.top ?: 0
            val consumeY = if (containerTop > stickyHeight) {
                when {
                    childScrollY > 0 && dy < 0 -> 0
                    containerTop - dy < stickyHeight -> top - stickyHeight
                    else -> dy
                }
            } else if (containerTop == stickyHeight) {
                if (-dy < childScrollY) 0 else dy + childScrollY
            } else {
                dy
            }

            if (consumeY != 0) {
                consumed[1] = consumeY
                scrollBy(0, consumeY)
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        (childViewHelper.container?.top == stickyHeight).let { currentStickTop ->
            if (currentStickTop != innerStickyTop) {
                innerStickyTop = currentStickTop
                onStickyListener?.invoke(innerStickyTop)
            }
        }
    }

    private fun onInterceptChildTouch(
        e: MotionEvent?,
        parent: View?,
        child: ChildRecyclerView?
    ): Boolean {
        if (e == null || parent == null || child == null || ViewCompat.isAttachedToWindow(parent)) {
            return false
        }
        val location = IntArray(2)
        child.getLocationOnScreen(location)
        if (e.rawY > location[1]) {
            return true
        }

        if (parent.top == stickyHeight) {
            return true
        }

        return false
    }

    private fun requestChildViewLayout() {
        childViewHelper.container?.let { parent ->
            val layoutParams = parent.layoutParams
            val newHeight = this.height - stickyHeight
            if (newHeight != layoutParams.height) {
                layoutParams.height = newHeight
                parent.layoutParams = layoutParams
            }
        }
    }

    fun setChildViewHelperCallback(callback: ChildViewHelper.Callback?) {
        childViewHelper.callback = callback
    }

    class ChildViewHelper(var callback: Callback? = null) {

        var child: ChildRecyclerView? = null
        var container: View? = null

        interface Callback {
            fun findCurrentChildRecyclerView(viewHolder: ViewHolder): ChildRecyclerView?
        }
    }

}