package com.ako.taypad

import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.profiledata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewOtherProfileActivity : AppCompatActivity() {
    lateinit var toolbar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_other_profile)
        val id=intent.getIntExtra("id",0)
        Log.d("id",id.toString())
        val sharedPreferences = getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        val jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        val name=findViewById<TextView>(R.id.name)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = "Back"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val blur=findViewById<ImageView>(R.id.blurimage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blur.setRenderEffect(RenderEffect.createBlurEffect(
                    30f, //radius X
                    30f, //Radius Y
                    Shader.TileMode.REPEAT ))
        }
        val call=RetrofitClient.JsonApi.getotherprofiledata("Bearer $jwt",id.toString())
        call.enqueue(object :Callback<profiledata>{
            override fun onResponse(call: Call<profiledata>, response: Response<profiledata>) {
                if(response.code()==200){
                    name.text=response.body()!!.username
                }
            }

            override fun onFailure(call: Call<profiledata>, t: Throwable) {

            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}