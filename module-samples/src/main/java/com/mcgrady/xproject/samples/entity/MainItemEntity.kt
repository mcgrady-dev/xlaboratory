package com.mcgrady.xproject.samples.entity


/**
 * Created by mcgrady on 2022/11/24.
 */
data class MainItemEntity(val id: Int, val name: String, val color: Int, val drawableText: String? = null) {

    companion object {
        const val ITEM_DIALOG_FRAGMENT = 1
        const val ITEM_CUSTOM_VIEW = 2
        const val ITEM_WINDOW_INSETS_CTL = 3
        const val ITEM_SHAPE = 4
        const val ITEM_SERVICE = 5
        const val ITEM_FRAGMENT = 6
        const val ITEM_VIEWPAGER = 7
        const val ITEM_VIEWPAGER_2 = 8
        const val ITEM_TOUCH_EVENT_DISPATCHER_1 = 9
        const val ITEM_TOUCH_EVENT_DISPATCHER_2 = 10
        const val ITEM_NESTED_SCROLL_COLLAPSING_TOOLBAR = 11
        const val ITEM_STICKY_RECYCLER_VIEW = 12
    }
}
