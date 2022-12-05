package com.mcgrady.xproject.samples.entity

import com.mcgrady.xproject.samples.base.MultiItemEntity


data class FeatureItemEntity(override val itemType: Int = ITEM_FEATURE) : MultiItemEntity {

    companion object {
        const val ITEM_FEATURE = 2
    }
}