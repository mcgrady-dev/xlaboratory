package com.mcgrady.xproject.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcgrady.xproject.samples.databinding.ContentHeaderBinding

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
}