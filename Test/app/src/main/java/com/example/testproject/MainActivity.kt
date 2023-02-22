package com.example.testproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.example.testproject.Adapter.viewPagerAdapter
import com.example.testproject.Fragment.FilterFragment
import com.example.testproject.Fragment.NewsFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val viewPagerAdapter = viewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(NewsFragment(), "News")
        viewPagerAdapter.addFragment(FilterFragment(), "Filter")
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want to exist")
        builder.setPositiveButton("OK") { dialog, which ->
            finish()
            super.onBackPressed()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
        }
        builder.show()
    }
}