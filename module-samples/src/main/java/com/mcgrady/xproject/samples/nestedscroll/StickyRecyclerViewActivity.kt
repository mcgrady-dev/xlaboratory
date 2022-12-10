package com.mcgrady.xproject.samples.nestedscroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcgrady.xproject.samples.adapter.SampleStickyMultiItemAdapter
import com.mcgrady.xproject.samples.data.DataServer
import com.mcgrady.xproject.samples.databinding.ActivityStickyRecyclerViewBinding

class StickyRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStickyRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStickyRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.parentRecyclerView.setChildViewHelperCallback(object: ParentRecyclerView.ChildViewHelper.Callback {
            override fun findCurrentChildRecyclerView(viewHolder: RecyclerView.ViewHolder): ChildRecyclerView? {
                return if (viewHolder is SampleStickyMultiItemAdapter.FeedsViewHolder)
                    ChildRecyclerView.reflect(viewHolder.binding.pager)
                else null
            }
        })

        binding.parentRecyclerView.adapter = SampleStickyMultiItemAdapter(DataServer.getSampleStickyData())
    }
}