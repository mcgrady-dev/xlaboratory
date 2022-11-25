package com.mcgrady.xproject.samples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.mcgrady.xproject.samples.databinding.ActivityMainBinding
import com.mcgrady.xproject.samples.dialogfragment.FirstDialogFragment
import com.mcgrady.xproject.samples.fragment.FragmentSampleActivity
import com.mcgrady.xproject.samples.shape.ShapeActivity
import com.mcgrady.xproject.samples.fragment.WindowInsetsControllerActivity
import com.mcgrady.xproject.samples.service.SampleServiceActivity
import com.mcgrady.xproject.samples.view.CustomViewActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapter = MainItemAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(MainItemBean.getItems())
        adapter.itemClickListener = object: MainItemAdapter.OnItemClickListener {
            override fun onItemClick(item: MainItemBean) {
                when (item.id) {
                    MainItemBean.ITEM_DIALOG_FRAGMENT -> {
                        FirstDialogFragment().showNow(supportFragmentManager, "FirstDialogFragment")
                    }
                    MainItemBean.ITEM_CUSTOM_VIEW -> {
                        startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
                    }
                    MainItemBean.ITEM_WINDOW_INSETS_CTL -> {
                        startActivity(Intent(this@MainActivity, WindowInsetsControllerActivity::class.java))
                    }
                    MainItemBean.ITEM_SHAPE -> {
                        startActivity(Intent(this@MainActivity, ShapeActivity::class.java))
                    }
                    MainItemBean.ITEM_SERVICE -> {
                        startActivity(Intent(this@MainActivity, SampleServiceActivity::class.java))
                    }
                    MainItemBean.ITEM_FRAGMENT -> {
                        startActivity(Intent(this@MainActivity, FragmentSampleActivity::class.java))
                    }
                    else -> {}
                }
            }
        }
    }
}