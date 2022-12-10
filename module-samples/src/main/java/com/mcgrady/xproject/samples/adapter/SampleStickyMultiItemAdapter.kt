package com.mcgrady.xproject.samples.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.base.BaseMultiItemAdapter
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.data.DataServer
import com.mcgrady.xproject.samples.databinding.ItemFeedsBinding
import com.mcgrady.xproject.samples.databinding.ItemHeaderBannerViewBinding
import com.mcgrady.xproject.samples.databinding.ItemHeaderFunctionsBinding
import com.mcgrady.xproject.samples.databinding.ItemHeaderSpecialAreaBinding
import com.mcgrady.xproject.samples.databinding.ItemHeaderTitlebarBinding
import com.mcgrady.xproject.samples.entity.BannerItemEntity
import com.mcgrady.xproject.samples.entity.HeaderFunctionsItemEntity
import com.mcgrady.xproject.samples.entity.HeaderSpecialAreaItemEntity
import com.mcgrady.xproject.samples.entity.HeaderTitleBarItemEntity

/**
 * Created by mcgrady on 2022/12/9.
 */
class SampleStickyMultiItemAdapter(override var items: List<MultiItemEntity>) : BaseMultiItemAdapter<MultiItemEntity>(items) {

    init {
        addItemType(ITEM_HEADER_BANNER, object: OnMultiItemAdapterListener<MultiItemEntity, HeaderBannerViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): HeaderBannerViewHolder {
                val binding = ItemHeaderBannerViewBinding.inflate(LayoutInflater.from(context), parent, false)
                return HeaderBannerViewHolder(binding)
            }

            override fun onBind(
                holder: HeaderBannerViewHolder,
                position: Int,
                item: MultiItemEntity?
            ) {
                item?.let {
                    holder.bindTo(it as BannerItemEntity)
                }
            }
        }).addItemType(ITEM_HEADER_FUNCTIONS, object: OnMultiItemAdapterListener<MultiItemEntity, HeaderFunctionsViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): HeaderFunctionsViewHolder {
                val binding = ItemHeaderFunctionsBinding.inflate(LayoutInflater.from(context), parent, false)
                return HeaderFunctionsViewHolder(binding)
            }

            override fun onBind(
                holder: HeaderFunctionsViewHolder,
                position: Int,
                item: MultiItemEntity?
            ) {
                item?.let {
                    holder.bindTo(it as HeaderFunctionsItemEntity)
                }
            }
        }).addItemType(ITEM_HEADER_SPECIAL_AREA, object: OnMultiItemAdapterListener<MultiItemEntity, HeaderSpecialAreaViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): HeaderSpecialAreaViewHolder {
                val binding = ItemHeaderSpecialAreaBinding.inflate(LayoutInflater.from(context), parent, false)
                return HeaderSpecialAreaViewHolder(binding)
            }

            override fun onBind(
                holder: HeaderSpecialAreaViewHolder,
                position: Int,
                item: MultiItemEntity?
            ) {

            }
        }).addItemType(ITEM_HEADER_TITLE_BAR, object: OnMultiItemAdapterListener<MultiItemEntity, HeaderTitleBarViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): HeaderTitleBarViewHolder {
                val binding = ItemHeaderTitlebarBinding.inflate(LayoutInflater.from(context), parent, false)
                return HeaderTitleBarViewHolder(binding)
            }

            override fun onBind(
                holder: HeaderTitleBarViewHolder,
                position: Int,
                item: MultiItemEntity?
            ) {

            }
        }).addItemType(ITEM_FEEDS, object: OnMultiItemAdapterListener<MultiItemEntity, FeedsViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): FeedsViewHolder {
                val binding = ItemFeedsBinding.inflate(LayoutInflater.from(context), parent, false)
                return FeedsViewHolder(binding)
            }

            override fun onBind(
                holder: FeedsViewHolder,
                position: Int,
                item: MultiItemEntity?
            ) {
                item?.let { holder.bindTo(it) }
            }
        }).onItemViewType { position, list -> list[position].itemType }
    }

    companion object {
        const val ITEM_HEADER_BANNER = 1
        const val ITEM_HEADER_FUNCTIONS = 2
        const val ITEM_HEADER_SPECIAL_AREA = 3
        const val ITEM_HEADER_TITLE_BAR = 4
        const val ITEM_FEEDS = 5
    }

    inner class HeaderBannerViewHolder(val binding: ItemHeaderBannerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: BannerItemEntity) {
            recyclerView.findViewTreeLifecycleOwner()?.let { owner ->
                binding.bannerView.addLifecycleObserver(owner)
            }
            binding.bannerView.adapter = SampleBannerAdapter(item.data)
        }
    }

    inner class HeaderFunctionsViewHolder(val binding: ItemHeaderFunctionsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: HeaderFunctionsItemEntity) {

        }
    }

    inner class HeaderSpecialAreaViewHolder(val binding: ItemHeaderSpecialAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: HeaderSpecialAreaItemEntity) {

        }
    }

    inner class HeaderTitleBarViewHolder(val binding: ItemHeaderTitlebarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: HeaderTitleBarItemEntity) {

        }
    }

    inner class FeedsViewHolder(val binding: ItemFeedsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: MultiItemEntity) {

        }
    }
}