package com.mcgrady.xproject.samples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.mcgrady.xproject.samples.adapter.MainItemAdapter
import com.mcgrady.xproject.samples.databinding.ActivityMainBinding
import com.mcgrady.xproject.samples.dialogfragment.FirstDialogFragment
import com.mcgrady.xproject.samples.entity.MainItemEntity
import com.mcgrady.xproject.samples.fragment.SampleFragmentActivity
import com.mcgrady.xproject.samples.shape.ShapeActivity
import com.mcgrady.xproject.samples.navigation.WindowInsetsControllerActivity
import com.mcgrady.xproject.samples.nestedscroll.CollapsingToolbarLayoutActivity
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
        adapter.submitList(MainItemEntity.getItems())
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            when (item.id) {
                MainItemEntity.ITEM_DIALOG_FRAGMENT -> {
                    FirstDialogFragment().showNow(supportFragmentManager, "FirstDialogFragment")
                }
                MainItemEntity.ITEM_CUSTOM_VIEW -> {
                    startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
                }
                MainItemEntity.ITEM_WINDOW_INSETS_CTL -> {
                    startActivity(Intent(this@MainActivity, WindowInsetsControllerActivity::class.java))
                }
                MainItemEntity.ITEM_SHAPE -> {
                    startActivity(Intent(this@MainActivity, ShapeActivity::class.java))
                }
                MainItemEntity.ITEM_SERVICE -> {
                    startActivity(Intent(this@MainActivity, SampleServiceActivity::class.java))
                }
                MainItemEntity.ITEM_FRAGMENT -> {
                    startActivity(Intent(this@MainActivity, SampleFragmentActivity::class.java))
                }
                MainItemEntity.ITEM_NESTED_SCROLL_COLLAPSING_TOOLBAR -> {
                    startActivity(Intent(this@MainActivity, CollapsingToolbarLayoutActivity::class.java))
                }
                else -> {}
            }
        }
    }
}