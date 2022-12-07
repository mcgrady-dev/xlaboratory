package com.mcgrady.xproject.samples.entity

import com.mcgrady.xproject.samples.base.MultiItemEntity


data class BannerItemEntity(override val itemType: Int = ITEM_BANNER, val data: List<BannerEntity> = emptyList()) : MultiItemEntity {

    companion object {
        const val ITEM_BANNER = 3
    }
}