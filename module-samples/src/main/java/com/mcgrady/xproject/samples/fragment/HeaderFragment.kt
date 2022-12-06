package com.mcgrady.xproject.samples.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcgrady.xproject.samples.adapter.SampleBannerAdapter
import com.mcgrady.xproject.samples.data.DataServer
import com.mcgrady.xproject.samples.databinding.ContentHeaderBinding
import com.mcgrady.xproject.samples.drawable.TextDrawable
import com.mcgrady.xproject.samples.util.Utils

/**
 * Created by mcgrady on 2022/11/28.
 */
class HeaderFragment : Fragment() {

    private lateinit var binding: ContentHeaderBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContentHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bannerView.run {
            addLifecycleObserver(this@HeaderFragment)
            adapter = SampleBannerAdapter(DataServer.getSampleBannerData(3))
        }

    }
}