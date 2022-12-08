package com.mcgrady.xproject.samples.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.base.BaseAdapter
import java.lang.ref.WeakReference
import kotlin.math.abs

/**
 * Created by mcgrady on 2022/12/6.
 */
class BannerView<T, BA : BaseAdapter<T, RecyclerView.ViewHolder>> @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), DefaultLifecycleObserver {

    private var startPosition: Int = DEFAULT_START_POSITION
    private var viewPager: ViewPager2
    var adapter: BA? = null
        set(value) {
            if (value == null) {
                return
            }

            val newList = createLoopFakeData(value.items) ?: emptyList()
            value.items = newList

            value.registerAdapterDataObserver(adapterDataObserver)
            viewPager.adapter = value
            viewPager.setCurrentItem(startPosition, false)
            field = value
        }
    @Orientation
    var orientation: Int = ORIENTATION_HORIZONTAL
        set(value) {
            viewPager.orientation = value
            field = value
        }
    private var touchSlop: Int
    private var defaultOnPageChangeCallback: OnPageChangeCallback
    private var onPageChangeCallback: OnPageChangeCallback? = null
    private var compositePageTransformer: CompositePageTransformer

    private var isLoop: Boolean = DEFAULT_IS_LOOP
        set(value) {
            field = value
            if (!value) {
                autoPlay = false
            }
        }
    private var autoPlay: Boolean = DEFAULT_AUTO_PLAY
    private var looper: Looper<T, BA>
    private var intervalTime: Long = DEFAULT_INTERVAL_TIME.toLong()

    var scrollTime = DEFAULT_SCROLL_TIME
        set(value) {
            field = value
            val layoutManager = getLayoutManager()
            if (layoutManager is SpeedLinearLayoutManager) {
                layoutManager.scrollTime = value
            }
        }
    private var isIntercept: Boolean = true
    private var isViewPagerDragging = false
    private var startX: Float = 0F
    private var startY: Float = 0F

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2
        compositePageTransformer = CompositePageTransformer()
        defaultOnPageChangeCallback = OnBannerPageChangeCallback()
        looper = Looper(this)
        viewPager = ViewPager2(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //offscreenPageLimit = 2
            registerOnPageChangeCallback(defaultOnPageChangeCallback)
            setPageTransformer(compositePageTransformer)
        }
//        if (intervalTime >= 100) {
//            SpeedLinearLayoutManager.invoke(viewPager, scrollTime)
//        }
        viewPager.currentItem = 1
        (viewPager.getChildAt(0) as RecyclerView?)?.let { recyclerView ->
            recyclerView.isNestedScrollingEnabled = false
        }
        addView(viewPager)

        attributeSet?.let { attrs ->
            initAttribute(attrs, defStyle)
        }
    }

    private fun initAttribute(attrs: AttributeSet, defStyle: Int = 0) {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyle, 0)

        intervalTime =
            attributes.getInt(R.styleable.BannerView_interval_time, DEFAULT_INTERVAL_TIME).toLong()
        autoPlay = attributes.getBoolean(R.styleable.BannerView_autoplay, DEFAULT_AUTO_PLAY)
        isLoop = attributes.getBoolean(R.styleable.BannerView_is_polling, DEFAULT_IS_LOOP)
        orientation = attributes.getInt(R.styleable.BannerView_orientation, ORIENTATION_HORIZONTAL)
        attributes.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startPolling()
    }

    override fun onStart(owner: LifecycleOwner) {
        startPolling()
    }

    override fun onStop(owner: LifecycleOwner) {
        stopPolling()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        viewPager.unregisterOnPageChangeCallback(defaultOnPageChangeCallback)
        stopPolling()
    }

    fun submitList(list: List<T>?) {
        val newList = createLoopFakeData(list)
        adapter?.submitList(newList)
        startPolling()
    }

    private fun createLoopFakeData(originList: List<T>?):List<T>? {
        if (originList == null || originList.isEmpty()) {
            return originList
        }

        return listOf(originList.last()) + originList + listOf(originList.first())
    }

    fun startPolling() {
        if (autoPlay) {
            stopPolling()
            postDelayed(looper, intervalTime)
        }
    }

    fun stopPolling() {
        if (autoPlay) {
            removeCallbacks(looper)
        }
    }

    fun setCurrentItem(position: Int, smoothScroll: Boolean = true) {
        viewPager.setCurrentItem(position, smoothScroll)
    }

    fun getCurrentItem(): Int = viewPager.currentItem
    fun getItemCount(): Int = adapter?.itemCount ?: 0

    fun setBannerClickListener(listener: BaseAdapter.OnItemClickListener<T>?) {
        adapter?.setOnItemClickListener(listener)
    }

    fun addOnPageChangeCallback(listener: OnPageChangeCallback?) {
        onPageChangeCallback = listener
    }

    fun addPageTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.addTransformer(transformer)
    }

    fun removeTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.removeTransformer(transformer)
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager? =
        (viewPager.getChildAt(0) as RecyclerView?)?.layoutManager

    internal class Looper<T, BA : BaseAdapter<T, RecyclerView.ViewHolder>>(bannerView: BannerView<T, BA>) :
        Runnable {
        private val reference: WeakReference<BannerView<T, BA>>

        init {
            reference = WeakReference<BannerView<T, BA>>(bannerView)
        }

        override fun run() {
            val bannerView: BannerView<T, BA>? = reference.get()
            bannerView?.let {
                if (it.autoPlay) {
                    val count: Int = it.adapter?.itemCount ?: 0
                    if (count == 0) {
                        return
                    }
                    val next: Int = (it.getCurrentItem() + 1) % count
                    Log.d(TAG, "looper: next：${it.getCurrentItem()}+1%${count}=$next")
                    it.setCurrentItem(next)
                    it.postDelayed(it.looper, it.intervalTime)
                }
            }
        }
    }

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (getItemCount() <= 1) {
                stopPolling()
            } else {
                startPolling()
            }
        }
    }

    fun addLifecycleObserver(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent: ev=$ev")
        if (viewPager.isUserInputEnabled) {
            when (ev?.actionMasked) {
                MotionEvent.ACTION_UP or MotionEvent.ACTION_CANCEL or MotionEvent.ACTION_OUTSIDE -> {
                    startPolling()
                }
                MotionEvent.ACTION_DOWN -> {
                    stopPolling()
                }
                else -> {}
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ev=$ev, touchSlop=$touchSlop")
        if (viewPager.isUserInputEnabled && isIntercept) {
            when (ev?.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = ev.x
                    val endY = ev.y
                    val distanceX = abs(endX - startX)
                    val distanceY = abs(endY - startY)
                    isViewPagerDragging = if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                        distanceX > touchSlop && distanceX > distanceY
                    } else {
                        distanceY > touchSlop && distanceY > distanceX
                    }
                    Log.d(TAG, "isViewPagerDragging=$isViewPagerDragging")
                    parent.requestDisallowInterceptTouchEvent(isViewPagerDragging)
                }
                MotionEvent.ACTION_UP or MotionEvent.ACTION_CANCEL -> {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                else -> {}
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent: ev=$event")
        return super.onTouchEvent(event)
    }

    inner class OnBannerPageChangeCallback : OnPageChangeCallback() {

        private var isDragging = false

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            Log.d(TAG, "onPageScrolled: position=$position, positionOffset=$positionOffset, positionOffsetPixels=$positionOffsetPixels")
            onPageChangeCallback?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageChangeCallback?.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            Log.d(TAG, "onPageScrollStateChanged: state=$state")

            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                val currentItem = viewPager.currentItem
                val size = adapter?.items?.size ?: 0

                when (currentItem) {
                    size - 1 -> viewPager.setCurrentItem(1, false)
                    0 -> viewPager.setCurrentItem(size - 2, false)
                }

                if (isDragging) {
                    isDragging = false
                    startPolling()
                }
            } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.d(TAG, "onPageScrollStateChanged: hasCallbacks=${handler.hasCallbacks(looper)}")
                    if (autoPlay && handler.hasCallbacks(looper)) {
                        handler.removeCallbacks(looper)
                        isDragging = true
                    }
                }
            }

            onPageChangeCallback?.onPageScrollStateChanged(state)
        }
    }

    companion object {

        const val TAG = "BannerView"
        const val INVALID_VALUE = -1
        const val DEFAULT_INTERVAL_TIME = 3500
        const val DEFAULT_AUTO_PLAY = true
        const val DEFAULT_IS_LOOP = true
        const val DEFAULT_INCREASE_COUNT = 2
        const val DEFAULT_SCROLL_TIME = 650
        const val DEFAULT_START_POSITION = 1
    }
}