package com.mcgrady.xproject.samples

/**
 * Created by mcgrady on 2022/11/24.
 */
data class MainItemBean(val id: Int, val name: String) {

    companion object {
        fun getItems(): List<MainItemBean> = arrayListOf(
            MainItemBean(ITEM_DIALOG_FRAGMENT, "DialogFragment"),
            MainItemBean(ITEM_CUSTOM_VIEW, "CustomView"),
            MainItemBean(ITEM_WINDOW_INSETS_CTL, "WindowInsertsController"),
            MainItemBean(ITEM_SHAPE, "Shape"),
            MainItemBean(ITEM_SERVICE, "Service"),
            MainItemBean(ITEM_FRAGMENT, "Fragment"),
        )

        const val ITEM_DIALOG_FRAGMENT = 1
        const val ITEM_CUSTOM_VIEW = 2
        const val ITEM_WINDOW_INSETS_CTL = 3
        const val ITEM_SHAPE = 4
        const val ITEM_SERVICE = 5
        const val ITEM_FRAGMENT = 6
    }
}
