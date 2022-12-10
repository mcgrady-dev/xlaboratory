package com.mcgrady.xproject.samples.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.base.BaseMultiItemAdapter
import com.mcgrady.xproject.samples.base.MultiItemEntity
import com.mcgrady.xproject.samples.entity.FeatureItemEntity
import com.mcgrady.xproject.samples.entity.NewsItemEntity
import com.mcgrady.xproject.samples.databinding.ItemFeatureViewBinding
import com.mcgrady.xproject.samples.databinding.ItemNewsViewBinding
import com.mcgrady.xproject.samples.databinding.ItemViewBannerBinding
import com.mcgrady.xproject.samples.entity.BannerItemEntity
/**
 * Created by mcgrady on 2022/11/28.
 */
class SampleMultipleItemAdapter : BaseMultiItemAdapter<MultiItemEntity>() {

    init {
        addItemType(ITEM_NEWS, object: OnMultiItemAdapterListener<MultiItemEntity, NewsViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): NewsViewHolder {
                val binding = ItemNewsViewBinding.inflate(LayoutInflater.from(context), parent, false)
                return NewsViewHolder(binding)
            }

            override fun onBind(holder: NewsViewHolder, position: Int, item: MultiItemEntity?) {
                if (item is NewsItemEntity) {
                    holder.bindTo(item)
                }
            }
        }).addItemType(ITEM_FEATURE, object: OnMultiItemAdapterListener<MultiItemEntity, FeatureViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): FeatureViewHolder {
                val binding = ItemFeatureViewBinding.inflate(LayoutInflater.from(context), parent, false)
                return FeatureViewHolder(binding)
            }

            override fun onBind(holder: FeatureViewHolder, position: Int, item: MultiItemEntity?) {
                if (item is FeatureItemEntity) {
                    holder.bindTo(item)
                }
            }
        }).addItemType(ITEM_BANNER, object : OnMultiItemAdapterListener<MultiItemEntity, BannerViewHolder> {
            override fun onCreate(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): BannerViewHolder {
                val binding = ItemViewBannerBinding.inflate(LayoutInflater.from(context), parent, false)
                return BannerViewHolder(binding)
            }

            override fun onBind(holder: BannerViewHolder, position: Int, item: MultiItemEntity?) {
                item?.let {
                    holder.bindTo(it as BannerItemEntity)
                }
            }

        }).onItemViewType { position, list -> list[position].itemType }
    }

    companion object {
        const val ITEM_NEWS = 1
        const val ITEM_FEATURE = 2
        const val ITEM_BANNER = 3
    }

    private inner class NewsViewHolder(val binding: ItemNewsViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: NewsItemEntity) {
            val msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\""
            binding.tv1.text = msg
        }
    }

    private inner class FeatureViewHolder(val binding: ItemFeatureViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: FeatureItemEntity) {
            when (layoutPosition % 3) {
                0 -> binding.iv.setImageResource(R.mipmap.animation_img1)
                1 -> binding.iv.setImageResource(R.mipmap.animation_img2)
                2 -> binding.iv.setImageResource(R.mipmap.animation_img3)
                else -> {}
            }
        }
    }

    private inner class BannerViewHolder(val binding: ItemViewBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: BannerItemEntity) {
            recyclerView.findViewTreeLifecycleOwner()?.let { owner ->
                binding.bannerView.addLifecycleObserver(owner)
            }
            binding.bannerView.adapter = SampleBannerAdapter(item.data)
        }
    }

}