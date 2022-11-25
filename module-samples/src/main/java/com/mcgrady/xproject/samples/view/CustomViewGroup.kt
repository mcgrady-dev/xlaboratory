package com.mcgrady.xproject.samples.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import com.mcgrady.xproject.samples.view.CustomViewActivity.Companion.getAction

/**
 * Created by mcgrady on 2022/11/17.
 */
class CustomViewGroup(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(CustomViewActivity.INPUT_TAG, "$TAG dispatchTouchEvent event=${getAction(ev?.action)} return super")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(CustomViewActivity.INPUT_TAG, "$TAG onInterceptTouchEvent event=${getAction(ev?.action)} return super")
//        if (ev?.action == MotionEvent.ACTION_MOVE) {
//            Log.e(CustomViewActivity.INPUT_TAG, "$TAG onInterceptTouchEvent event=${getAction(ev?.action)} return true")
//            return true
//        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(CustomViewActivity.INPUT_TAG, "$TAG onTouchEvent event=${getAction(event?.action)} return super")
        return super.onTouchEvent(event)
    }

    companion object {
        private const val TAG = "CustomViewGroup"
    }
}