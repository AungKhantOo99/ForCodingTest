package com.ako.taypad

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.getpartsdata.getpartsdata
import com.ako.taypad.ui.Libraryitems.CurrentRead
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryDetailsActivity : AppCompatActivity(){
    lateinit var titel: TextView
    lateinit var read: TextView
    lateinit var rating: TextView
    lateinit var review: TextView
    lateinit var desccription: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var toolbar: Toolbar
    lateinit var viewModel: AllData
    lateinit var startread: Button
    var currentposition = 0
    lateinit var img:ImageView
    var jwt=""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_details)
        read = findViewById(R.id.read)
        rating = findViewById(R.id.rating)
        review = findViewById(R.id.review)
        startread=findViewById(R.id.start_read)
        titel = findViewById(R.id.title)
        desccription = findViewById(R.id.descrription)
        img=findViewById(R.id.titleimg)
        val position = intent.getIntExtra("Key",  0)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = "Back"
        val SharedPreferences = this.getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        jwt = SharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        val call=RetrofitClient.JsonApi.getparts("Bearer $jwt","$position")
        call.enqueue(object :Callback<getpartsdata>{
            override fun onResponse(call: Call<getpartsdata>, response: Response<getpartsdata>) {
                val data=response.body()
                if(response.code()==200){
                    titel.text=data!!.data.title
                    desccription.text=data!!.data.description
                    if(data.data.coverUrl!=null){
                        Picasso.get().load("http://192.168.100.147:1337${data!!.data.coverUrl}")
                            .into(img)
                    }else{
                        img.setImageResource(R.drawable.one)
                    }
                    startread.setOnClickListener {
                        val int=Intent(this@StoryDetailsActivity,PartsDetailActivity::class.java)
                        int.putExtra("position",data!!.data.parts[0].id)
                        startActivity(int)
                    }
                }
            }

            override fun onFailure(call: Call<getpartsdata>, t: Throwable) {

            }

        })
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        val item: MenuItem = menu.findItem(R.id.save)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.save){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to add this story to library")
            builder.setPositiveButton("OK") { dialog, which ->

            }
            builder.setNegativeButton("Cancel") { dialog, which ->

            }
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
       super.onBackPressed()
    }
}