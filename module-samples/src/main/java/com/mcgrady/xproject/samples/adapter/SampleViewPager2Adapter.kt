package com.mcgrady.xproject.samples.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by mcgrady on 2022/12/2.
 */
class SampleViewPager2Adapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    fun addFragments(vararg fragments: Fragment) {
        fragmentList.addAll(fragments)
    }
}