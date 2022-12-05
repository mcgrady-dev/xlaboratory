package com.mcgrady.xproject.samples.entity

import com.mcgrady.xproject.samples.base.MultiItemEntity

data class NewsItemEntity(override val itemType: Int = ITEM_NEWS) : MultiItemEntity {

    companion object {
        const val ITEM_NEWS = 1
    }
}
