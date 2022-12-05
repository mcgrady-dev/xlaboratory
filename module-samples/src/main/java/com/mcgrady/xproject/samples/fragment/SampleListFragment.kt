package com.mcgrady.xproject.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcgrady.xproject.samples.adapter.SampleMultipleItemAdapter
import com.mcgrady.xproject.samples.data.DataServer
import com.mcgrady.xproject.samples.entity.FeatureItemEntity
import com.mcgrady.xproject.samples.entity.NewsItemEntity
import com.mcgrady.xproject.samples.databinding.FragmentListBinding
import com.mcgrady.xproject.samples.entity.FeatureItemEntity.Companion.ITEM_FEATURE
import com.mcgrady.xproject.samples.entity.NewsItemEntity.Companion.ITEM_NEWS

/**
 * Created by mcgrady on 2022/11/28.
 */
class SampleListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SampleMultipleItemAdapter(DataServer.getSampleMultiData(10))
        binding.recyclerView.adapter = adapter
    }
}