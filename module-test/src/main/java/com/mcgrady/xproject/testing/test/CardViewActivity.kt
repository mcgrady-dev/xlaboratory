package com.mcgrady.xproject.testing.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcgrady.xproject.testing.test.databinding.ActivityCardViewBinding

class CardViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityCardViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}