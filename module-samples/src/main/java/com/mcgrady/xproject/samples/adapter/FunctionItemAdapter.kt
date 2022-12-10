package com.mcgrady.xproject.samples.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.base.BaseAdapter
import com.mcgrady.xproject.samples.databinding.ItemFunctionViewBinding
import com.mcgrady.xproject.samples.databinding.ItemMainBinding
import com.mcgrady.xproject.samples.drawable.TextDrawable
import com.mcgrady.xproject.samples.entity.FunctionItemEntity
import com.mcgrady.xproject.samples.entity.MainItemEntity
import com.mcgrady.xproject.samples.util.Utils

/**
 * Created by mcgrady on 2022/11/24.
 */
class FunctionItemAdapter : BaseAdapter<FunctionItemEntity, FunctionItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFunctionViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: FunctionItemEntity?) {
        item?.let {
            holder.bindTo(it)
        }
    }

    inner class ViewHolder(private val binding: ItemFunctionViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: FunctionItemEntity) {
            val drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .buildRound(item.name.first().toString(), item.color)

            binding.ivIcon.setImageDrawable(drawable)
            binding.tvName.text = item.name
        }
    }
}