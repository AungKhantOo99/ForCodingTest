package com.example.testproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.Adapter.FilterResultAdapter
import com.example.testproject.model.products
import java.util.ArrayList

class FilterResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_result)
        supportActionBar!!.title="Filter Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val data=intent.getSerializableExtra("Keys") as ArrayList<products>
        val recycler=findViewById<RecyclerView>(R.id.filterview)
        recycler.layoutManager= GridLayoutManager(this,2)
        recycler.adapter=FilterResultAdapter(this,data)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}