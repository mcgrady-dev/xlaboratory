package com.mcgrady.xproject.samples

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.databinding.ItemMainBinding

/**
 * Created by mcgrady on 2022/11/24.
 */
class MainItemAdapter : ListAdapter<MainItemBean, MainItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    open var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(itemBean: MainItemBean) {
            binding.name.text = itemBean.name
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(itemBean)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MainItemBean)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainItemBean>() {
            override fun areItemsTheSame(oldItem: MainItemBean, newItem: MainItemBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MainItemBean, newItem: MainItemBean): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}