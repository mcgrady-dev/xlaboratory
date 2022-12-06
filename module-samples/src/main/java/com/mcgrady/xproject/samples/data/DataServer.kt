package com.mcgrady.xproject.samples.data

import com.mcgrady.xproject.samples.base.BannerEntity
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.entity.FeatureItemEntity
import com.mcgrady.xproject.samples.entity.NewsItemEntity
import com.mcgrady.xproject.samples.util.Utils

/**
 * Created by mcgrady on 2022/12/2.
 */
object DataServer {

    fun getSampleMultiData(lenth: Int): List<MultiItemEntity> {
        val list = mutableListOf<MultiItemEntity>()
        for (i in 0..lenth) {
            list.add(NewsItemEntity())
            list.add(FeatureItemEntity())
        }
        return list
    }

    fun getSampleBannerData(length: Int): List<BannerEntity> {
        val list = mutableListOf<BannerEntity>()
        for (i in 1..length) {
            list.add(BannerEntity(i, Utils.getRandomColor(false)))
        }

        return list
    }
}
