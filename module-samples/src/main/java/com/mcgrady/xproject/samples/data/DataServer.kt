package com.mcgrady.xproject.samples.data

import com.mcgrady.xproject.samples.adapter.SampleStickyMultiItemAdapter
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.entity.*
import com.mcgrady.xproject.samples.util.Utils

/**
 * Created by mcgrady on 2022/12/2.
 */
object DataServer {

    fun getMainItemData(): List<MainItemEntity> = arrayListOf(
        MainItemEntity(MainItemEntity.ITEM_NESTED_SCROLL_COLLAPSING_TOOLBAR, "CoordinatorLayout", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_STICKY_RECYCLER_VIEW, "Sticky\nRecyclerView", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_DIALOG_FRAGMENT, "DialogFragment", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_CUSTOM_VIEW, "CustomView", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_WINDOW_INSETS_CTL, "WindowInserts\nController", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_SHAPE, "Shape", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_SERVICE, "Service", Utils.getRandomColor(false)),
        MainItemEntity(MainItemEntity.ITEM_FRAGMENT, "Fragment", Utils.getRandomColor(false)),
//            MainItemBean(ITEM_VIEWPAGER, "ViewPager"),
//            MainItemBean(ITEM_VIEWPAGER_2, "ViewPager2"),
        MainItemEntity(MainItemEntity.ITEM_TOUCH_EVENT_DISPATCHER_1, "TouchEvent\nDispatcher1", Utils.getRandomColor(false), drawableText = "T1"),
        MainItemEntity(MainItemEntity.ITEM_TOUCH_EVENT_DISPATCHER_2, "TouchEvent\nDispatcher2", Utils.getRandomColor(false), drawableText = "T2"),
    )

    fun getSampleMultiData(length: Int): List<MultiItemEntity> {
        val list = mutableListOf<MultiItemEntity>()
        for (i in 0..length) {
            list.add(NewsItemEntity())
            list.add(NewsItemEntity())
            if (i % 2 == 0) {
                list.add(BannerItemEntity(data = getSampleBannerData()))
            } else {
                list.add(FeatureItemEntity())
            }
        }
        return list
    }

    fun getSampleBannerData(length: Int = 3): List<BannerData> {
        val list = mutableListOf<BannerData>()
        for (i in 1..length) {
            list.add(BannerData(i, Utils.getRandomColor(false)))
        }

        return list
    }

    fun getSampleStickyData(): List<MultiItemEntity> {
        return mutableListOf(
            BannerItemEntity(itemType = SampleStickyMultiItemAdapter.ITEM_HEADER_BANNER, data = getSampleBannerData()),
            HeaderFunctionsItemEntity(itemType = SampleStickyMultiItemAdapter.ITEM_HEADER_FUNCTIONS, list = getSampleFunctionItemData()),
        )
    }

    fun getSampleFunctionItemData(length: Int = 5): List<FunctionItemEntity> {
        val list = mutableListOf<FunctionItemEntity>()
        for (i in 0 until length) {
            list.add(FunctionItemEntity(Utils.getRandomString(length = 5), Utils.getRandomColor(false)))
        }
        return list
    }
}
