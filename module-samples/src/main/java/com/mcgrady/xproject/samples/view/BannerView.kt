package com.mcgrady.xproject.samples.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.base.BaseAdapter
import java.lang.ref.WeakReference

/**
 * Created by mcgrady on 2022/12/6.
 */
class BannerView<T, BA : BaseAdapter<T, RecyclerView.ViewHolder>> @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), DefaultLifecycleObserver {

    private var viewPager: ViewPager2
    var adapter: BA? = null
        set(value) {
            if (value == null) {
                return
            }

            value.registerAdapterDataObserver(adapterDataObserver)
            viewPager.adapter = value
            viewPager.setCurrentItem(1, false)
            field = value
        }
    @Orientation var orientation: Int = ORIENTATION_HORIZONTAL
        set(value) {
            viewPager.orientation = value
            field = value
        }
    private var touchSlop: Int
    private var defaultOnPageChangeCallback: OnPageChangeCallback
    private var onPageChangeCallback: OnPageChangeCallback? = null
    private var compositePageTransformer: CompositePageTransformer

    var isPolling: Boolean = false
    private var autoPlay: Boolean = false
    private var looper: Looper<T, BA>
    private var intervalTime: Long = DEFAULT_INTERVAL_TIME.toLong()
        set(value) {
            field = value
            val layoutManager = getLayoutManager()
            if (layoutManager is SpeedLinearLayoutManager) {
                layoutManager.intervalTime = value.toInt()
            }
        }

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
            offscreenPageLimit = 2
            registerOnPageChangeCallback(defaultOnPageChangeCallback)
            setPageTransformer(compositePageTransformer)
        }
//        if (intervalTime >= 100) {
//            SpeedLinearLayoutManager.invoke(viewPager, intervalTime.toInt())
//        }
        viewPager.currentItem = 1
        addView(viewPager)

        attributeSet?.let { attrs ->
            initAttribute(attrs, defStyle)
        }

        if (!isPolling) {
            autoPlay = false
        }
    }

    private fun initAttribute(attrs: AttributeSet, defStyle: Int = 0) {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyle, 0)

        intervalTime = attributes.getInt(R.styleable.BannerView_interval_time, DEFAULT_INTERVAL_TIME).toLong()
        autoPlay = attributes.getBoolean(R.styleable.BannerView_autoplay, DEFAULT_AUTO_PLAY)
        isPolling = attributes.getBoolean(R.styleable.BannerView_is_polling, DEFAULT_IS_POLLING)
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
        adapter?.submitList(list)
        startPolling()
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

    fun addOnPageChangeCallback(listener: OnPageChangeCallback) {
        onPageChangeCallback = listener
    }

    fun addPageTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.addTransformer(transformer)
    }

    fun removeTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.removeTransformer(transformer)
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager?
        = (viewPager.getChildAt(0) as RecyclerView?)?.layoutManager

    internal class Looper<T, BA: BaseAdapter<T, RecyclerView.ViewHolder>>(bannerView: BannerView<T, BA>) : Runnable {
        private val reference: WeakReference<BannerView<T, BA>>

        init {
            reference = WeakReference<BannerView<T, BA>>(bannerView)
        }

        override fun run() {
            val bannerView: BannerView<T, BA>? = reference.get()
            bannerView?.let {
                if (it.autoPlay) {
                    val count: Int = bannerView.adapter?.itemCount ?: 0
                    if (count == 0) {
                        return
                    }
                    val next: Int = (bannerView.getCurrentItem() + 1) % count
                    Log.d("AutoPlay", "currentItem=${bannerView.getCurrentItem()} count=$count next=$next")
                    val currentItem = bannerView.getCurrentItem() + 1
                    bannerView.setCurrentItem(currentItem)
                    bannerView.postDelayed(bannerView.looper, bannerView.intervalTime)
                }
            }
        }
    }

    private val adapterDataObserver = object: RecyclerView.AdapterDataObserver() {
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

    inner class OnBannerPageChangeCallback : OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
//            val size = adapter?.items?.size ?: 0
//            if (position == size - 1) {
//                viewPager.setCurrentItem(1, false)
//            } else if (position == 0) {
//                viewPager.setCurrentItem(size - 2, false)
//            }
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                val currentItem = viewPager.currentItem
                val size = adapter?.items?.size ?: 0

                when (currentItem) {
                    size - 1 -> viewPager.setCurrentItem(1, false)
                    0 -> viewPager.setCurrentItem(size - 2, false)
                }

//                if (currentItem == 1) {
//                    viewPager.setCurrentItem(size - 3, false)
//                } else if (currentItem == size - 2) {
//                    viewPager.setCurrentItem(2, false)
//                }
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

//    inner class OnBannerPageChangeCallback : OnPageChangeCallback() {
//        private var mTempPosition: Int = -1
//        private var isScrolled = false
//        override fun onPageScrolled(
//            position: Int,
//            positionOffset: Float,
//            positionOffsetPixels: Int
//        ) {
//            val realPosition: Int = getRealPosition(isPolling, position, getRealCount())
//            if (realPosition == getCurrentItem() - 1) {
//                onPageChangeCallback?.onPageScrolled(
//                    realPosition,
//                    positionOffset,
//                    positionOffsetPixels
//                )
//            }
//            /*if (getIndicator() != null && realPosition == getCurrentItem() - 1) {
//                getIndicator().onPageScrolled(realPosition, positionOffset, positionOffsetPixels)
//            }*/
//        }
//
//        override fun onPageSelected(position: Int) {
//            if (isScrolled) {
//                mTempPosition = position
//                val realPosition: Int = getRealPosition(isPolling, position, getRealCount())
//                onPageChangeCallback?.onPageSelected(realPosition)
//                /*if (getIndicator() != null) {
//                    getIndicator().onPageSelected(realPosition)
//                }*/
//            }
//        }
//
//        override fun onPageScrollStateChanged(state: Int) {
//            //手势滑动中,代码执行滑动中
//            if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
//                isScrolled = true
//            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
//                //滑动闲置或滑动结束
//                isScrolled = false
//                if (mTempPosition != INVALID_VALUE && isPolling) {
//                    if (mTempPosition == 0) {
//                        setCurrentItem(getRealCount(), false)
//                    } else if (mTempPosition == getItemCount() - 1) {
//                        setCurrentItem(1, false)
//                    }
//                }
//            }
//            onPageChangeCallback?.onPageScrollStateChanged(state)
//
//            /*if (getIndicator() != null) {
//                getIndicator().onPageScrollStateChanged(state)
//            }*/
//        }
//    }

    companion object {
//        fun getRealPosition(isPolling: Boolean, position: Int, realCount: Int): Int {
//            return if (isPolling) {
//                position
//            } else when(position) {
//                0 -> realCount - 1
//                (realCount + 1) -> 0
//                else -> position - 1
//            }
//        }

        const val INVALID_VALUE = -1
        const val DEFAULT_INTERVAL_TIME = 3000
        const val DEFAULT_AUTO_PLAY = true
        const val DEFAULT_IS_POLLING = true
        const val DEFAULT_INCREASE_COUNT = 2
    }
}

inline fun invokeLayoutManager() {

}