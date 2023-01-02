package com.ako.taypad

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.SearchResultAdapter
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.getallstories.allstoriesItem

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
        val SharedPreferences = getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        val jwt = SharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        val viewModel= ViewModelProvider(this).get(AllData::class.java)
        viewModel.getdata(jwt).observe(this, Observer {
            val failterlist=ArrayList<allstoriesItem>()
            it.forEach {
                        if (it.category.name.equals(result)){
                           failterlist.add(it)
                    }
            }
            recycler.adapter=SearchResultAdapter(this,failterlist)
        })

//        viewModel.getExample().observe(this, Observer {
//            val filterlist=ArrayList<bindexample>()
//            it.forEach {
//                if(it.tags.equals(result)){
//                    filterlist.add(it)
//                }
//            }
//            recycler.adapter= SearchResultAdapter(this,filterlist)
//        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}