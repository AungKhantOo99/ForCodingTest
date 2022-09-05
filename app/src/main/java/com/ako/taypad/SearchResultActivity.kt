package com.ako.taypad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ako.taypad.Adapter.BookDetailAdapter
import com.ako.taypad.Adapter.SearchResultAdapter
import com.ako.taypad.Adapter.viewPagerAdapter
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.example.bindexample
import com.ako.taypad.ui.Libraryitems.CurrentRead
import com.ako.taypad.ui.Libraryitems.Favouriate
import com.ako.taypad.ui.Libraryitems.ReadingList
import com.google.android.material.tabs.TabLayout

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val result=intent.getStringExtra("Key")
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val recycler=findViewById<RecyclerView>(R.id.searchresult)
        recycler.layoutManager=LinearLayoutManager(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title=result
        val viewModel= ViewModelProvider(this).get(AllData::class.java)
        viewModel.getExample().observe(this, Observer {
            val filterlist=ArrayList<bindexample>()
            it.forEach {
                if(it.tags.equals(result)){
                    filterlist.add(it)
                }
            }
            recycler.adapter= SearchResultAdapter(this,filterlist)
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}