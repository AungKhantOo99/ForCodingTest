package com.ako.taypad.Adapter

import android.content.Context
import android.content.Intent
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

class HomeAdapter(val context: Context,val binddata:ArrayList<bindexample>):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.firstshow,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.titel).text=binddata[position].titel.toString()
        holder.itemView.findViewById<ImageView>(R.id.photo)
            .setImageResource(binddata[position].img)
        holder.itemView.setOnClickListener {
            val intent=Intent(context,StoryDetailsActivity::class.java)
            intent.putExtra("Key",position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return binddata.size
    }
}