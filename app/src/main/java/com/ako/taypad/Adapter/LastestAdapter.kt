package com.ako.taypad.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.StoryDetailsActivity
import com.ako.taypad.model.ResponseData
import com.ako.taypad.model.example.bindexample

class LastestAdapter(val context: Context,val binddata:ArrayList<bindexample>):RecyclerView.Adapter<LastestAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = 0
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastestAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.lastest,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LastestAdapter.ViewHolder, position: Int) {

        holder.itemView.findViewById<ImageView>(R.id.lastestimg)
            .setImageResource(binddata[position].img)
        holder.itemView.findViewById<TextView>(R.id.tags).text=binddata[position].tags
        holder.itemView.setOnClickListener {
            val intent= Intent(context, StoryDetailsActivity::class.java)
            intent.putExtra("Key",position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return binddata.size
    }
}