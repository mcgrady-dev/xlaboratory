package com.mcgrady.xproject.samples.data

import com.mcgrady.xproject.samples.entity.BannerEntity
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.entity.BannerItemEntity
import com.mcgrady.xproject.samples.entity.FeatureItemEntity
import com.mcgrady.xproject.samples.entity.NewsItemEntity
import com.mcgrady.xproject.samples.util.Utils

/**
 * Created by mcgrady on 2022/12/2.
 */
object DataServer {

    fun getSampleMultiData(length: Int): List<MultiItemEntity> {
        val list = mutableListOf<MultiItemEntity>()
        for (i in 0..length) {
            list.add(NewsItemEntity())
            list.add(NewsItemEntity())
            if (i % 2 == 0) {
                list.add(BannerItemEntity(data = getSampleBannerData(3)))
            } else {
                list.add(FeatureItemEntity())
            }
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
