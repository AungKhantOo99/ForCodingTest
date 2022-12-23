package com.ako.taypad

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.CommentAdapter
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.comment.Data
import com.ako.taypad.model.comment.commentdata
import com.ako.taypad.model.comment.commetresponce
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class check : AppCompatActivity() {
    lateinit var commentvalue:EditText
    lateinit var cmtadapter:CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        val id=intent.getIntExtra("id",0)
        bindData(id)
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun bindData(id: Int) {
        val sharedPreferences = getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE
        )
        val jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null)
        val viewModel = ViewModelProvider(this).get(AllData::class.java)
        viewModel.allParts(jwt!!, id).observe(this, Observer {
            val data = it
            Log.d("whatwrong", it.partInfo.title)
            val btnClose = findViewById<ImageView>(R.id.close)
            commentvalue = findViewById(R.id.cmt)
            val allcomment = findViewById<RecyclerView>(R.id.recyclerforcomments)
            allcomment.layoutManager = LinearLayoutManager(this)
            cmtadapter = CommentAdapter(this)
            cmtadapter.setlist(data.comments)
            allcomment.adapter = cmtadapter
            cmtadapter.notifyDataSetChanged()
            btnClose.setOnClickListener {
                comment(jwt,id)
            }

        })
    }
    private fun comment(jwt:String,partid: Int) {
        val data = commentdata(Data(commentvalue.text.toString(), partid.toString()))
        val call = RetrofitClient.JsonApi.comment("Bearer $jwt", data)
        call.enqueue(object : Callback<commetresponce> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<commetresponce>,
                response: Response<commetresponce>
            ) {
                if (response.code() == 200) {
                    commentvalue.text.clear()
                    cmtadapter.notifyDataSetChanged()
                    bindData(partid)

                }
            }

            override fun onFailure(call: Call<commetresponce>, t: Throwable) {
            }

        })
    }

}