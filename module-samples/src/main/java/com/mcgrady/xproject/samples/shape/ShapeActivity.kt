package com.mcgrady.xproject.samples.shape

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mcgrady.xproject.samples.databinding.ActivityShapeSampleBinding

class ShapeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShapeSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShapeSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnChangeShapeSoild.setOnClickListener {
            //动态修改Shape的solid属性的color值
            val drawable = binding.viewShape.background as GradientDrawable
            drawable.color = ContextCompat.getColorStateList(this@ShapeActivity,
                android.R.color.darker_gray
            )
        }
    }
}
