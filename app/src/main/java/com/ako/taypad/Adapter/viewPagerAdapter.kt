package com.ako.taypad.Adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class viewPagerAdapter(fragmentManager: FragmentManager):
    FragmentPagerAdapter(fragmentManager)
{
    private val fragments: ArrayList<Fragment>
    private val titles: ArrayList<String>
    init{
        fragments =ArrayList()
        titles= ArrayList()
    }
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
    override fun getCount(): Int {
        return 3
    }
    fun addFragment(fragment: Fragment, title: String)
    {
        fragments.add(fragment)
        titles.add(title)
    }
    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}