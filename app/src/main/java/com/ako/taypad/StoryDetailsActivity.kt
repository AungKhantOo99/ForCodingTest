package com.ako.taypad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.ako.taypad.Adapter.BookDetailAdapter
import com.ako.taypad.Interface.bindselectbook
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.example.bindexample
import com.ako.taypad.ui.Library.LibraryFragment
import com.ako.taypad.ui.Libraryitems.CurrentRead
import kotlin.properties.Delegates

class StoryDetailsActivity : AppCompatActivity(),bindselectbook {
    lateinit var titel:TextView
    lateinit var desccription:TextView
    lateinit var recyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var toolbar:Toolbar
    lateinit var viewModel: AllData
    var currentposition=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var allbook=ArrayList<bindexample>()
        setContentView(R.layout.activity_story_details)
        val position=intent.getIntExtra("Key",0)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        viewModel= ViewModelProvider(this).get(AllData::class.java)
        viewModel.getExample().observe(this, Observer {
            allbook=it
            bindbookdetail(it,position)
            toolbar.title=allbook[position].titel
            recyclerView.adapter=BookDetailAdapter(this,it,this)
        })

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        titel=findViewById(R.id.title)
        desccription=findViewById(R.id.descrription)
        recyclerView=findViewById(R.id.bookdetail)
        mLayoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView.layoutManager=mLayoutManager
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.smoothScrollToPosition(position)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(mLayoutManager) as View
                    currentposition= mLayoutManager.getPosition(centerView)
                    this@StoryDetailsActivity.bindSelectBook(currentposition)
                }
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun bindbookdetail(data:ArrayList<bindexample>,id:Int){
        toolbar.title=data[id].titel
        titel.text=data[id].titel
        desccription.text= data[id].description
    }
    override fun bindSelectBook(getid: Int) {
        viewModel.getExample().observe(this, Observer {
           bindbookdetail(it,getid)
        })
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to add this story to library")
        builder.setPositiveButton("OK") { dialog, which ->
            viewModel.getExample().observe(this, Observer {
                CurrentRead.fav.add(it[currentposition].titel)
                finish()
            })
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            finish()
        }
        builder.show()

    }
}