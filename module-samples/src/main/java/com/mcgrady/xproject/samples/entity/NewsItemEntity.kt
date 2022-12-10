package com.mcgrady.xproject.samples.entity

import com.mcgrady.xproject.samples.adapter.SampleMultipleItemAdapter.Companion.ITEM_NEWS
import com.mcgrady.xproject.samples.base.MultiItemEntity

data class NewsItemEntity(override val itemType: Int = ITEM_NEWS) : MultiItemEntity {
}
