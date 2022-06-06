package com.mcgrady.xproject.testing.samples.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcgrady.xproject.testing.samples.databinding.ActivityImageViewScaleTypeSampleBinding

class ImageViewScaleTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageViewScaleTypeSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageViewScaleTypeSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}