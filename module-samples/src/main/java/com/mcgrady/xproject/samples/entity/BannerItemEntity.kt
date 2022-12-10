package com.mcgrady.xproject.samples.entity

import com.mcgrady.xproject.samples.adapter.SampleMultipleItemAdapter.Companion.ITEM_BANNER
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.data.BannerData


data class BannerItemEntity(override val itemType: Int = ITEM_BANNER, val data: List<BannerData> = emptyList()) : MultiItemEntity {
}