package com.mcgrady.xproject.samples.base

import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.view.BannerView
import com.mcgrady.xproject.samples.view.BannerView.Companion.DEFAULT_INCREASE_COUNT

/**
 * Created by mcgrady on 2022/12/6.
 */
abstract class BaseBannerAdapter<T, VH : RecyclerView.ViewHolder>(open val originList: List<T>) :
    BaseAdapter<T, VH>(
        if (originList.isNotEmpty()) listOf(originList.last()) + originList + listOf(
            originList.first()
        ) else originList
    )