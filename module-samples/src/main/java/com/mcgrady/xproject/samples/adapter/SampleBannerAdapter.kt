package com.mcgrady.xproject.samples.adapter

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.base.BaseAdapter
import com.mcgrady.xproject.samples.data.BannerData
import com.mcgrady.xproject.samples.drawable.TextDrawable

/**
 * Created by mcgrady on 2022/12/6.
 */
class SampleBannerAdapter(override var items: List<BannerData>) : BaseAdapter<BannerData, SampleBannerAdapter.ViewHolder>(items) {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val imageView = AppCompatImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: BannerData?) {
        item?.let {
            holder.imageView.setImageDrawable(
                TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .textColor(Color.WHITE)
                    .endConfig()
                    .buildRect(it.number.toString(), it.color)
            )
        }
    }

    inner class ViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {

    }
}