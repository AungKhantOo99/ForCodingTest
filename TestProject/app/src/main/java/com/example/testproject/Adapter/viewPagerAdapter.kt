@file:Suppress("DEPRECATION")

package com.example.testproject.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class viewPagerAdapter (fragmentManager: FragmentManager):
    FragmentPagerAdapter(fragmentManager)
{
    private val fragments: ArrayList<Fragment> = ArrayList()
    private val titles: ArrayList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
    override fun getCount(): Int {
        return fragments.size
    }
    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title.lowercase())
    }
    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}