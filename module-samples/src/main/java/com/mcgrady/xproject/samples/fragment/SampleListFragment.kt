package com.mcgrady.xproject.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcgrady.xproject.samples.adapter.SampleMultipleItemAdapter
import com.mcgrady.xproject.samples.base.LazyFragment
import com.mcgrady.xproject.samples.data.DataServer
import com.mcgrady.xproject.samples.databinding.FragmentListBinding

/**
 * Created by mcgrady on 2022/11/28.
 */
class SampleListFragment : LazyFragment() {

    private lateinit var binding: FragmentListBinding

    override fun lazyInit() {
        (binding.recyclerView.adapter as SampleMultipleItemAdapter?)?.submitList(DataServer.getSampleMultiData(10))
    }

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
        binding.recyclerView.adapter = SampleMultipleItemAdapter()
    }
}