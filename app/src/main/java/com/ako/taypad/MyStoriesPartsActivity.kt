package com.ako.taypad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.MyPartsAdapter
import com.ako.taypad.Adapter.MyStoriesAdapter
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.getpartsdata.Part
import com.ako.taypad.model.getpartsdata.getpartsdata
import com.ako.taypad.model.sendstorydata.getdata
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyStoriesPartsActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var addnewpart:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_stories_parts)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recycler=findViewById(R.id.allpartsview)
        addnewpart=findViewById(R.id.addnewpart)
        val SharedPreferences = this.getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        val jwt = SharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        val position = intent.getIntExtra("Key",  0)
        val call= RetrofitClient.JsonApi.getparts("Bearer $jwt","$position")
        call.enqueue(object : Callback<getpartsdata> {
            override fun onResponse(call: Call<getpartsdata>, response: Response<getpartsdata>) {
                val alldata=response.body()!!.data
                val data=response.body()!!.data.parts as ArrayList<Part>
                val postdata=getdata(alldata.contentRating,alldata.coverUrl,alldata.createdAt,
                                         alldata.description,alldata.id,alldata.status,alldata.title,alldata.updatedAt)
                recycler.layoutManager=LinearLayoutManager(this@MyStoriesPartsActivity)
                recycler.adapter=MyPartsAdapter(this@MyStoriesPartsActivity,data)
                addnewpart.setOnClickListener {
                    val int = Intent(this@MyStoriesPartsActivity, StoryPartsActivity::class.java)
                    int.putExtra("postdata", postdata)
                    startActivity(int)
                }
            }

            override fun onFailure(call: Call<getpartsdata>, t: Throwable) {

            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}