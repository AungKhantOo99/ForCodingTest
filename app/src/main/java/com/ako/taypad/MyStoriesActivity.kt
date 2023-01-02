package com.ako.taypad

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.MyStoriesAdapter
import com.ako.taypad.ViewModel.AllData

class MyStoriesActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var jwt: String
    lateinit var recycler: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var viewmodel : AllData
    lateinit var homeAdapter: MyStoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_stories)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE
        )
        jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        recycler=findViewById(R.id.mystoriesview)
        layoutManager= LinearLayoutManager(this)
        viewmodel = ViewModelProvider(this).get(AllData::class.java)
        viewmodel.getmystories(jwt).observe(this, Observer {
           Log.d("Mystories",it.toString())
             homeAdapter= MyStoriesAdapter(this,it)
            recycler.adapter=homeAdapter
            recycler.layoutManager=layoutManager
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}