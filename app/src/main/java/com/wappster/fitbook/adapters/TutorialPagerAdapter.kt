package com.wappster.fitbook.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.wappster.fitbook.ui.tutorial.TutorialFragment

class TutorialPagerAdapter(fragmentManager: FragmentManager, private val titles: Array<String>, private val images: Array<Int>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return TutorialFragment.newInstance(titles[position], images[position])
    }

    override fun getCount(): Int {
        return titles.size
    }
}