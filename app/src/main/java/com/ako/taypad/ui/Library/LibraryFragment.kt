package com.ako.taypad.ui.Library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ako.taypad.Adapter.viewPagerAdapter
import com.ako.taypad.R
import com.ako.taypad.ui.Libraryitems.CurrentRead
import com.ako.taypad.ui.Libraryitems.Favouriate
import com.ako.taypad.ui.Libraryitems.ReadingList
import com.google.android.material.tabs.TabLayout


class LibraryFragment : Fragment() {
    var count=0
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_library, container, false)
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        val viewPagerAdapter = viewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(CurrentRead(), "Current Read")
        viewPagerAdapter.addFragment(ReadingList(), "Reading LIst")
        viewPagerAdapter.addFragment(Favouriate(), "Favouriate")
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}

