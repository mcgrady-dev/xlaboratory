package com.mcgrady.xproject.samples.nestedscroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.adapter.SampleViewPager2Adapter
import com.mcgrady.xproject.samples.databinding.ActivityCollapsingToolbarLayoutBinding
import com.mcgrady.xproject.samples.fragment.HeaderFragment
import com.mcgrady.xproject.samples.fragment.SampleListFragment
import kotlin.math.abs

class CollapsingToolbarLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollapsingToolbarLayoutBinding

    private var tabLayoutMediator: TabLayoutMediator? = null
    private var fixedBehavior: FlingFixBehavior? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCollapsingToolbarLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(findViewById(R.id.toolbar))
//        binding.toolbarLayout.title = title
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offsetHeight = appBarLayout.totalScrollRange
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                binding.toolbar.alpha = 1F
            } else {
                binding.toolbar.alpha = 0F
            }
        }

//        binding.appBar.post {
//            val behavior: AppBarLayout.Behavior =
//                (binding.appBar.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior
//            behavior.setDragCallback(object : DragCallback() {
//                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
//                    return true
//                }
//            })
//        }

//        fixedBehavior = (binding.appBar.layoutParams as CoordinatorLayout.LayoutParams).behavior as FixedBehavior?
//
//        binding.customCoordinatorLayout.listener =
//            CustomCoordinatorLayout.OnInterceptTouchListener {
//                fixedBehavior?.stopFling()
//            }

        supportFragmentManager.beginTransaction()
            .add(R.id.container_header, HeaderFragment())
            .commit()

        val adapter = SampleViewPager2Adapter(supportFragmentManager, lifecycle)
        adapter.addFragments(SampleListFragment(), SampleListFragment(), SampleListFragment())
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = "Tab $position"
        }.apply {
            attach()
        }

    }

    private fun populateTabsFromPagerAdapter() {
        binding.tabLayout.removeAllTabs()

        binding.pager.adapter?.let {
            val tab = binding.tabLayout.newTab()
            binding.tabLayout.addTab(tab, false)
        }
    }
}